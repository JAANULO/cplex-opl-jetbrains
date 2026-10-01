import json
import os
import sys
from pathlib import Path
from datetime import datetime

def main():
    base_dir = Path(__file__).parent.parent
    reports_dir = base_dir / "reports"
    reports_dir.mkdir(parents=True, exist_ok=True)

    summary_env = os.environ.get("GITHUB_STEP_SUMMARY")
    
    # Find latest json report (support both plugin-report-*.json and test-summary-*.json)
    json_files = sorted(
        list(reports_dir.glob("plugin-report-*.json")) + list(reports_dir.glob("test-summary-*.json")),
        key=lambda p: p.stat().st_mtime,
        reverse=True
    )

    if not json_files:
        msg = "### ❌ No plugin test reports found in reports/ directory.\n"
        if summary_env:
            with open(summary_env, "a", encoding="utf-8") as f:
                f.write(msg)
        print("No report files found in reports/.", file=sys.stderr)
        return

    latest_json = json_files[0]
    try:
        data = json.loads(latest_json.read_text(encoding="utf-8"))
    except Exception as e:
        print(f"Error reading {latest_json}: {e}", file=sys.stderr)
        sys.exit(1)

    timestamp = data.get("timestamp", datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    plugin_ver = data.get("pluginVersion", "unknown")
    res_type = data.get("result", "UNKNOWN")
    total = data.get("totalTests", 0)
    passed = data.get("successfulTests", 0)
    failed = data.get("failedTests", 0)
    skipped = data.get("skippedTests", 0)
    duration_ms = data.get("durationMs", 0)
    duration_s = duration_ms / 1000.0

    status_icon = "✅" if failed == 0 and res_type == "SUCCESS" else "❌"

    lines = []
    lines.append(f"### {status_icon} CPLEX OPL JetBrains Plugin — Test Report")
    lines.append(f"**Version:** `{plugin_ver}` | **Timestamp:** {timestamp}")
    lines.append(f"**Total:** {total} | **Passed:** {passed} | **Failed:** {failed} | **Skipped:** {skipped} | **Duration:** {duration_s:.2f}s ({duration_ms} ms)\n")

    tests = data.get("tests", [])
    failed_tests = [t for t in tests if len(t) >= 3 and t[2] not in ("SUCCESS", "PASSED")]

    if failed_tests:
        lines.append("#### ❌ Failed Tests")
        lines.append("| Class | Test Method | Status | Duration (ms) |")
        lines.append("|---|---|---|---|")
        for t in failed_tests:
            c_name, m_name, status, dur = t[0], t[1], t[2], t[3] if len(t) > 3 else "-"
            lines.append(f"| `{c_name}` | `{m_name}` | ❌ **{status}** | {dur} ms |")
        lines.append("")

    lines.append("#### 📋 Test Results Breakdown")
    lines.append("| Class | Test Method | Status | Duration (ms) |")
    lines.append("|---|---|---|---|")
    for t in tests:
        if len(t) < 3:
            continue
        c_name, m_name, status = t[0], t[1], t[2]
        dur = t[3] if len(t) > 3 else "-"
        icon = "✅" if status in ("SUCCESS", "PASSED") else ("⏸️" if status == "SKIPPED" else "❌")
        lines.append(f"| `{c_name}` | `{m_name}` | {icon} {status} | {dur} ms |")

    markdown_content = "\n".join(lines) + "\n"

    # Save timestamped MD report alongside the JSON
    md_filename = latest_json.stem + ".md"
    md_path = reports_dir / md_filename
    md_path.write_text(markdown_content, encoding="utf-8")
    print(f"Summary markdown saved to: {md_path}")

    # Write to GitHub Actions step summary if available
    if summary_env:
        with open(summary_env, "a", encoding="utf-8") as f:
            f.write(markdown_content)
        print("GitHub Step Summary updated successfully.")

if __name__ == "__main__":
    main()
