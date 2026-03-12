# BingChong Pest/Disease Warning Cloud System (病虫害预警云系统)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE) [![ASP.NET](https://img.shields.io/badge/tech-ASP.NET%20MVC%203-orange.svg)](https://docs.microsoft.com/en-us/aspnet/mvc/) [![Android](https://img.shields.io/badge/tech-Android-green.svg)](https://developer.android.com/) [![SQL Server](https://img.shields.io/badge/db-SQL%20Server-red.svg)](https://www.microsoft.com/en-us/sql-server/)

## 📋 Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Quick Start](#quick-start)
- [Detailed Setup](#detailed-setup)
- [Database](#database)
- [Deployment](#deployment)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [Troubleshooting](#troubleshooting)

## Overview
**BingChong** is a comprehensive cloud-based system for monitoring, forecasting, and managing agricultural pests and diseases (病虫害). It supports web admin dashboard, Android mobile app for field reporting, background services, and SQL Server backend.

- **Target Users**: Agricultural experts, county/city-level managers, field reporters.
- **Key Capabilities**: Pest reporting/mapping, station monitoring, analytics/charts, task management, user roles, video/expert integration.
- **Tech Stack**:
  | Component | Technologies |
  |-----------|--------------|
  | Backend | ASP.NET MVC 3 (.NET 4.0), LINQ-to-SQL, Entity Framework 4.1, NPOI (Excel) |
  | Frontend (Web) | jQuery 1.5, Bootstrap/Ace Admin, jqGrid, FullCalendar |
  | Mobile | Android (min SDK 8), Baidu Maps/LBS, Camera/GPS, AnyChat Video |
  | DB | SQL Server (remote: 218.25.54.56/Bingchong) |
  | Service | .NET Windows Service |

Project dates back to ~2014-2015, with Chinese documentation.

## Architecture
```
Android App <--> Backend API (Controllers) <--> SQL Server (Bingchong DB)
                      |
                BingchongService (Background)
```
- **Web**: Hosts admin views (ASPX/Razor hybrid).
- **Mobile**: Reports data via HTTP (inferred from activities).
- **Data Flow**: Field reports → DB → Analytics/Maps → Tasks/Notifications.

## Features
### Web Admin (Backend/BingChongBackend)
- **Disease/Pest Mgmt**: CRUD, image upload, import/export Excel (`DiseasePestController`).
- **Stations**: Add/view/manage monitoring stations (`StationController`).
- **Maps**: Baidu-integrated mapping/chat (`MapController`).
- **Analytics**: Histogram/line charts, summary stats (`AnalysisController`).
- **Tasks/Work**: Plans, notices, documents (`WorkingManageController`).
- **Users/Roles**: RBAC, regions (`AccountController`, `SettingsController`).
- **More**: New diseases, uploads, backups, help.

### Android App
- **Reporting**: Blight/pest/location reports with photos (`ReportActivity`).
- **History/Tasks**: View/complete tasks (`HistoryActivity`, `TaskActivity`).
- **Stats**: Graphs/polygons (`StatisticsActivity`).
- **Management**: Users, data/movies, messages (`HomeMgrActivity`).
- **Settings**: Contacts, functions, feedback.
- **Integrations**: Video calls (AnyChat), Baidu Maps, SMS.

## Quick Start
1. **DB**: Restore `Database/Bingchong.mdf` to local SQL Server (use `Restore.sql`).
2. **Backend**: Open `Backend/BingChongBackend.sln` in VS2010+, update `Web.config` connection string, F5.
3. **Service**: Build/run `Service/BingchongService.sln`.
4. **Android**: Open `Android/Bingchong` in Android Studio/Eclipse, connect to backend URL, build APK.
5. Access: Web at `http://localhost:49102`, App login.

## Detailed Setup
### Backend (.NET)
- **Requirements**: VS2010+, .NET 4.0, SQL Server, IIS Express.
- Update `Web.config`:
  ```xml
  <add name=\"BingchongConnectionString\" connectionString=\"Data Source=.;Initial Catalog=Bingchong;Integrated Security=True\" />
  ```
- NuGet: Restore from `packages.config` (EntityFramework, jQuery).
- Run: Build → View in browser.

### Android
- **Requirements**: Android Studio/Eclipse, SDK 8+.
- Update API endpoints (in Java sources, e.g., `src/com/` - Baidu API key: `a0ka1ZeZKMR9Tia8MfgQrPL0`).
- Keystore: `Document/bingchong_keystore.jks` (password in `keystore_password.txt`).
- Build: APK for ARM (libs included).

### Service
- Windows Service for scheduled tasks (e.g., backups via `Database/BackupSchedule.sql`).

## Database
- **Schema**: `Bingchong` DB with tables for pests, stations, users, regions, work plans.
- Scripts: `Backup.sql`, `Restore.sql`, `CleanupSchedule.sql`.
- LINQ Models: `Models/BingChongDB.dbml`, `MasterDB.dbml`.

## Deployment
- **Server**: IIS for web, SQL Server.
- **Android**: Sign with keystore, distribute APK.

## Screenshots
*(Add from `Backend/Content/DiseasePest/*.jpg` or prototypes `Document/Prototype/`) *
- Web Dashboard: [Prototype images](Document/Prototype/Prot/files/)
- App: Reporting screen, Map view.

## Contributing
1. Fork/Clone.
2. Create branch `feature/xxx`.
3. Commit & PR.

## Troubleshooting
- **Conn Errors**: Check SQL remote access (sa/gidshddprh!123).
- **Android GPS**: Baidu LBS key valid?

**Note**: Legacy project - consider modernization (ASP.NET Core, Kotlin, EF Core).

---

*Built with ❤️ for agricultural pest management.*

