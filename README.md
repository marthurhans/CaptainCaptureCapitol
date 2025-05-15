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
- Dropped TODO markers for DatePicker and Export features
- App rebuilt and verified stable with working Room DB (v4)

DatePicker Integration Completed (May 2025)
- Replaced manual date input with DatePickerDialogs across all screens
- Updated XML fields for start/end dates and excursion date to disable keyboard input
- Connected DatePickers to existing vacation and excursion data fields
- Preserved and reused existing validation logic with no format changes required
- Confirmed consistent date format (YYYY-MM-DD) app-wide
- Verified all pickers function as expected on emulator and physical device

