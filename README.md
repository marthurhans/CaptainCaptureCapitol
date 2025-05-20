<strong>**DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. **</strong>

# WESTERN GOVERNORS UNIVERSITY
## D424 – SOFTWARE ENGINEERING CAPSTONE

This mobile application allows users to create, manage, and track vacations along with associated excursions. It was originally developed for the D308 Mobile Application Development course and is being extended for the D424 Software Engineering Capstone.

## Capstone Enhancements
- Export excursion report to CSV or plain text
- Add unit tests for validation logic using JUnit
- Improve date validation and error handling
- Refactor UI and navigation for usability

## Tech Stack
- Java
- Android Studio
- Room DB (SQLite)
- XML layouts

## Deployment
The project is deployed via a signed APK and does not require cloud hosting. A final APK will be included for Capstone Task 4.

## Initial Status
- Project imported from D308
- App builds successfully
- Emulator tested and functional
- Enhancements and testing underway

## Author
Michael Hans  
WGU ID: 001107400

*This repository is for private academic use only and will not be made publicly accessible.*

## Development Progress

Refactor Phase Completed (May 2025)
- Verified and commented Vacation and Excursion models
- Added Room foreign key with cascade behavior
- Updated DAO query for date-sorted excursions
- Moved utility classes into new utils package
- Inserted TODO markers for DatePicker and Export features
- App rebuilt and verified stable with working Room DB (v4)

DatePicker Integration Completed (May 2025)
- Replaced manual date input with DatePickerDialogs across all screens
- Updated XML fields for start/end dates and excursion date to disable keyboard input
- Connected DatePickers to existing vacation and excursion data fields
- Preserved and reused existing validation logic
- Confirmed consistent date format (YYYY-MM-DD) app-wide
- Verified all pickers function as expected on emulator and physical device

Export Feature Completed (May 2025)
- Implemented full reporting system with both CSV and plain text output options
- Added `ExportUtils` with report generation and file-saving logic
  - Reports include all vacations and their associated excursions
- Confirmed proper formatting and coverage, including vacations with no excursions
- Introduced save-to-file functionality using internal storage (`/files/reports/`)
- Added UI export button on main screen with AlertDialog to choose format
- Enabled sharing reports via Android's share sheet (Gmail, Drive, etc.)
- Verified output paths via Logcat and manually accessed report files in emulator

Visual Polish & Launch Experience Completed (May 2025)
- Replaced old vacation and excursion lists with modern CardViews and better layout spacing
- Made all screens visually consistent using ripple effects, elevation, and Material-style buttons
- Replaced the launcher icon with a custom D424 project logo
- Added a splash screen at launch that shows the app’s logo before loading the main menu
- Polished navigation with visible buttons and headings across all major screens
- Added a confirmation popup before deleting vacations to prevent accidental data loss

Unit Testing Completed (May 2025)
- Created four test cases to validate app functionality
- Used `ValidationUtilsTest` to verify date format validation and range checking
- Added `ExportUtilsTest` to confirm formatting logic for generated reports
- Built `VacationDaoTest` to insert and retrieve vacation data using supplied Room database
- Confirmed test execution in both `test/` and `androidTest/` environments
- Captured passing test result screenshots for Task 3 documentation

