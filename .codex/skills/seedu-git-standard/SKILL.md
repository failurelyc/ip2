---
name: seedu-git-standard
description: Apply the SE-EDU Git conventions to commit messages and branch names in this project. Use when preparing, reviewing, amending, or proposing commits and branches.
---

# SE-EDU Git Standard

## Overview

Apply the SE-EDU [Git conventions](https://se-education.org/guides/conventions/git.html) whenever preparing a commit or branch name.

## Commit subjects

- Write a clear subject in imperative mood, capitalize its first letter, omit the final period, and keep it under 50 characters when possible (hard limit: 72).
- Add a meaningful scope or category prefix when useful, such as `Parser: Add deadline validation`.

## Commit bodies

- Add a body for non-trivial commits, separated from the subject by one blank line.
- Wrap body lines at 72 characters and separate paragraphs with blank lines.
- Explain what changed and why; do not describe implementation mechanics that the diff already shows.
- Structure the explanation around the current situation, why it needs to change, what to do, and why that approach is appropriate. Use present tense for the situation and imperative mood for the change.

## Branch names

- Use meaningful kebab-case keywords, such as `refactor-ui-tests`.
- For issue-related branches, use `<issue-number>-<kebab-case-title-keywords>`.

## Review workflow

1. Check the branch name against the naming rules.
2. Draft a concise imperative subject and verify its length, capitalization, and punctuation.
3. Add a 72-column body for non-trivial changes that explains what and why.
4. Do not commit or push unless explicitly authorized by the user.

**1. Workflow-Based** (best for sequential processes)
- Works well when there are clear step-by-step procedures
- Example: DOCX skill with "Workflow Decision Tree" -> "Reading" -> "Creating" -> "Editing"
- Structure: ## Overview -> ## Workflow Decision Tree -> ## Step 1 -> ## Step 2...

**2. Task-Based** (best for tool collections)
- Works well when the skill offers different operations/capabilities
- Example: PDF skill with "Quick Start" -> "Merge PDFs" -> "Split PDFs" -> "Extract Text"
- Structure: ## Overview -> ## Quick Start -> ## Task Category 1 -> ## Task Category 2...

**3. Reference/Guidelines** (best for standards or specifications)
- Works well for brand guidelines, coding standards, or requirements
- Example: Brand styling with "Brand Guidelines" -> "Colors" -> "Typography" -> "Features"
- Structure: ## Overview -> ## Guidelines -> ## Specifications -> ## Usage...

**4. Capabilities-Based** (best for integrated systems)
- Works well when the skill provides multiple interrelated features
- Example: Product Management with "Core Capabilities" -> numbered capability list
- Structure: ## Overview -> ## Core Capabilities -> ### 1. Feature -> ### 2. Feature...

Patterns can be mixed and matched as needed. Most skills combine patterns (e.g., start with task-based, add workflow for complex operations).

<!-- The detailed resource template is intentionally omitted because this standard needs no bundled resources. -->

<!-- No bundled resources are required. -->

<!-- Resources (optional)

Create only the resource directories this skill actually needs. Delete this section if no resources are required.

### scripts/
Executable code (Python/Bash/etc.) that can be run directly to perform specific operations.

**Examples from other skills:**
- PDF skill: `fill_fillable_fields.py`, `extract_form_field_info.py` - utilities for PDF manipulation
- DOCX skill: `document.py`, `utilities.py` - Python modules for document processing

**Appropriate for:** Python scripts, shell scripts, or any executable code that performs automation, data processing, or specific operations.

**Note:** Scripts may be executed without loading into context, but can still be read by Codex for patching or environment adjustments.

### references/
Documentation and reference material intended to be loaded into context to inform Codex's process and thinking.

**Examples from other skills:**
- Product management: `communication.md`, `context_building.md` - detailed workflow guides
- BigQuery: API reference documentation and query examples
- Finance: Schema documentation, company policies

**Appropriate for:** In-depth documentation, API references, database schemas, comprehensive guides, or any detailed information that Codex should reference while working.

### assets/
Files not intended to be loaded into context, but rather used within the output Codex produces.

**Examples from other skills:**
- Brand styling: PowerPoint template files (.pptx), logo files
- Frontend builder: HTML/React boilerplate project directories
- Typography: Font files (.ttf, .woff2)

**Appropriate for:** Templates, boilerplate code, document templates, images, icons, fonts, or any files meant to be copied or used in the final output.

---

Not every skill requires all three types of resources. -->
