pokaż jak mam poprawić te pliki # Automatyczne tworzenie changeloga (GitHub Actions)

Ten dokument opisuje 3 popularne sposoby automatyzacji changeloga. Wybierz podejscie zaleznie od stylu pracy (etykiety PR, conventional commits, automatyczne wersjonowanie).

## 1) Release Drafter (najprostsze, oparte o PR labels)

**Kiedy warto:**
- Nie chcesz zmieniac stylu commitow.
- Uzywasz etykiet PR (np. `feature`, `fix`).
- Chcesz automatycznie generowac Release Notes, a changelog aktualizowac recznie lub polautomatycznie.

**Wymagania:**
- PR-y musza miec etykiety.

**Konfiguracja (przyklad):**

`.github/release-drafter.yml`
```yaml
name-template: "v$RESOLVED_VERSION"
tag-template: "v$RESOLVED_VERSION"
change-template: "- $TITLE (#$NUMBER)"
categories:
  - title: "Added"
    labels: ["feature", "enhancement"]
  - title: "Fixed"
    labels: ["fix", "bug"]
  - title: "Changed"
    labels: ["chore", "refactor"]
```

`.github/workflows/release-drafter.yml`
```yaml
name: Release Drafter
on:
  push:
    branches: ["main"]
  pull_request:
    types: ["opened", "reopened", "synchronize", "labeled", "unlabeled"]

permissions:
  contents: write
  pull-requests: read

jobs:
  update_release_draft:
    runs-on: ubuntu-latest
    steps:
      - uses: release-drafter/release-drafter@v6
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

**Co zyskujesz:**
- Automatyczny szkic opisu wydania (Release Notes).
- Kategorie sekcji (Added/Fixed/Changed) na podstawie etykiet.

## 2) git-cliff (lekki generator changeloga z commitow)

**Kiedy warto:**
- Chcesz automatycznie aktualizowac `CHANGELOG.md`.
- Stosujesz (lub chcesz wprowadzic) Conventional Commits.

**Wymagania:**
- Commity w stylu Conventional Commits (np. `feat: ...`, `fix: ...`).

**Minimalna konfiguracja:**

`cliff.toml` (przyklad uproszczony)
```toml
[changelog]
header = "# CPLEX-Plugin Changelog\n\n"
body = """
## [Unreleased]
{{#each commits}}
- {{message}}
{{/each}}
"""
trim = true

[git]
conventional_commits = true
filter_unconventional = true
```

**Workflow (przyklad):**

`.github/workflows/changelog.yml`
```yaml
name: Generate Changelog
on:
  workflow_dispatch:
  push:
    tags: ["v*"]

permissions:
  contents: write

jobs:
  changelog:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0
      - uses: orhun/git-cliff-action@v3
        with:
          config: cliff.toml
          args: "--output CHANGELOG.md"
      - name: Commit changelog
        run: |
          git config user.name "github-actions"
          git config user.email "github-actions@github.com"
          git add CHANGELOG.md
          git commit -m "chore: update changelog" || exit 0
          git push
```

**Co zyskujesz:**
- Automatyczne aktualizacje `CHANGELOG.md` przy tagowaniu release.
- Daty wydan wynikaja z tagow (data taga).

## 3) Semantic Release / Changesets (pelna automatyzacja wersjonowania)

**Kiedy warto:**
- Chcesz automatycznie podbijac wersje, tagowac i publikowac release.
- Akceptujesz konwencje commitow albo pliki changesetow.

**Wymagania:**
- Conventional Commits (Semantic Release) lub pliki `.changeset/*.md` (Changesets).

**Plusy:**
- Automatycznie ustalane wersje, daty wydan, release notes.

**Minusy:**
- Wymaga zmiany workflow (konwencje commitow lub changesety).

## Jak wybrac podejscie

- Chcesz minimum zmian procesu -> **Release Drafter**.
- Chcesz automatycznie aktualizowac `CHANGELOG.md` z commitow -> **git-cliff**.
- Chcesz pelnej automatyzacji wersji i release -> **Semantic Release / Changesets**.

## Dobre praktyki

- Trzymaj aktualne sekcje `Unreleased` i dodawaj nowa wersje dopiero w dniu wydania.
- Ustal jasne etykiety PR (np. `feature`, `fix`, `chore`).
- Jesli wybierzesz Conventional Commits, wprowadz prosty lint (np. commitlint) aby trzymac format.

