
# 🚀 Rest-Assured API Automation Framework

This repository contains a REST API test automation framework built with:

- **Rest-Assured**
- **TestNG**
- **Allure Reports**
- **Gradle**
- **GitHub Actions** (CI)

---

## 📁 Project Structure

```

src/
├── main/java/         → POJOs and utility classes
└── test/java/         → Test classes
└── resources/
└── suite/     → TestNG XML files

.github/
├── workflows/         → CI workflows
└── actions/
└── upload-artifact/  → Vendored GitHub Action for Allure artifact upload

````

---

## ✅ Running Tests Locally

```bash
./gradlew clean test allureReport
````

To run with a custom suite:

```bash
./gradlew clean test -DsuiteXmlFile=src/test/resources/suite/regression.xml
```

---

## 📊 Generate Allure Report

```bash
./gradlew allureReport
allure serve build/allure-report
```

> Make sure [Allure CLI](https://docs.qameta.io/allure/#_installing_a_commandline) is installed.

---

## ⚙️ GitHub Workflows

### ⏰ Scheduled Workflow

Runs daily at 11 PM IST and uploads the Allure report.

File: `.github/workflows/nightly-allure-test.yml`

### 🧪 Manual Workflow

Triggered manually; user provides suite XML path.

File: `.github/workflows/manual-suite-run.yml`

---

## 📦 Local Action: Upload Artifact

The action `upload-artifact` is vendored inside:

```
.github/actions/upload-artifact/
```

Used to upload the Allure report locally without relying on external action references.

---

## 📘 Example TestNG Suite

```xml
<!-- src/test/resources/suite/regression.xml -->
<suite name="API Test Suite">
  <test name="RegisterUserTests">
    <classes>
      <class name="com.example.api_automation.tests.RegisterUserTest"/>
    </classes>
  </test>
</suite>
```
