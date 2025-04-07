# Aircraft Detector for Drone Pilots

A real-time Android application designed to enhance Unmanned Aerial Vehicle (UAV) safety by providing drone pilots with instant awareness of nearby manned aircraft using data from the ADS-B One database.

![App Screenshot](https://drive.google.com/uc?id=1grgi_IrU_1VDEbz03fMFfG5Xz_I7ZZQv)

## Overview

As UAV usage increases, so does the risk of collisions with manned aircraft at low altitudes. Our Android app helps mitigate this risk by alerting users when aircraft enter a user-defined safety radius. The app visually displays aircraft on a map interface and can trigger warnings via sound and pop-ups when potential collisions are detected.

## Features

- Real-time aircraft detection using the ADSB-One API
- Embedded Google Maps interface for visual awareness
- Customizable alarm system with sound and Snackbar alerts
- Adjustable detection radius (default: 5 Nautical Miles)
- Displays altitude, speed, and direction of aircraft
- Supports live GPS tracking of the drone pilot’s location

## Technologies Used

- Android Studio 2022.3.1
- Google Maps SDK for Android
- Retrofit for API integration
- Kotlin and Java
- Android 8.0+ compatible

## Test Environment

- Pixel 5 API 34 (Virtual)
- Pixel 7 Privacy Sandbox (Virtual)
- Samsung Galaxy A14 (Physical)
- Google Cloud API for map rendering

Test cases ensured:
- Robust response to multiple/rapid start/cancel presses
- Accuracy of detection radius changes
- Alert systems function as expected

## Screenshots

| Fall Prototype | Updated Map View | Alert Interface |
|----------------|------------------|------------------|
| ![Fig 2](https://drive.google.com/uc?id=1wGLAOEY9vNVVWMLz7PaL-r4cwi7V9wzy) | ![Fig 8](https://drive.google.com/uc?id=1VVrSr55jrm9vaWUAZzswFhmv-vK5zpuI) | ![Fig 1](https://drive.google.com/uc?id=1njRvmxxsknnBypcu8Cwgzd0CWJWhg2cv) |

## Licensing

This project is open-sourced under the MIT License, allowing free use, modification, and distribution under proper attribution.

## Development Process

Follows ISO 12207 software lifecycle standard and Kotlin style guide for maintainability and structured development. Full Git version control ensures team consistency.

## Demo Videos

- [Demo 1](https://drive.google.com/file/d/1grgi_IrU_1VDEbz03fMFfG5Xz_I7ZZQv/view?usp=sharing)
- [Demo 2](https://drive.google.com/file/d/1wGLAOEY9vNVVWMLz7PaL-r4cwi7V9wzy/view?usp=drive_link)
- [Demo 3](https://drive.google.com/file/d/1VVrSr55jrm9vaWUAZzswFhmv-vK5zpuI/view?usp=drive_link)
- [Demo 4](https://drive.google.com/file/d/1njRvmxxsknnBypcu8Cwgzd0CWJWhg2cv/view)

## Authors

- Cong-Bang Ta – [congbant@uci.edu](mailto:congbant@uci.edu)  
- Junichi Takashima – [junichit@uci.edu](mailto:junichit@uci.edu)  
- Qilin Ye – [qiliny3@uci.edu](mailto:qiliny3@uci.edu)  
- Zi Ye – [ziy25@uci.edu](mailto:ziy25@uci.edu)  
- Advisor: Prof. Peter Burke – [pburke@uci.edu](mailto:pburke@uci.edu)

## Acknowledgments

- ADS-B One for the real-time aircraft data API
- Professor Peter Burke for his guidance
- UCI School of Engineering & ICS for technical support
