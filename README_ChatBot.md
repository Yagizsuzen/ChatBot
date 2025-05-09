
# 📱 ChatBot Android Application

This project is a basic **ChatBot Android application** developed using **Java**, featuring a clean and modular architecture. It uses **Gradle Kotlin DSL** for build configuration and follows standard Android development practices.

## 📁 Project Structure

- `app/src/main/java/com/example/chatbot/`: Core Java classes including:
  - `MainActivity.java`, `ChatActivity.java` – Main screens
  - `MessageAdapter.java`, `ChatListAdapter.java` – RecyclerView-based adapters
  - `ApiService.java` – API communication handler
  - `SharedPreferencesUtil.java` – User settings and preferences manager
- `res/`: Layouts, themes, strings, and other Android resources
- `AndroidManifest.xml`: App manifest with permissions and declarations
- Gradle Kotlin DSL scripts: `.kts`-based project and module-level build files

## 🧠 Features

- 🗨️ **Real-time messaging** interface between the user and the bot
- 🎨 **Dark Mode** support for better visibility and reduced eye strain in low-light environments
- 👤 **Personalized greetings**: The bot addresses the user by the name they set in the settings  
  _Example: If you enter “Ali” → “Hello Ali!”, or if you enter “Yağız” → “Hi Yağız!”_
- ♻️ Clean and dynamic chat UI using `RecyclerView`
- 💾 **Preferences saved locally** via `SharedPreferences`

## ⚙️ Settings

- Switch between **Light Mode** and **Dark Mode**
- Enter your **preferred name** so the bot can greet you personally in conversations

## 🚀 Getting Started

1. Clone or download the repository:
   ```bash
   git clone https://github.com/your-username/chatbot-android.git
   cd chatbot-android
   ```

2. Open the project in **Android Studio**

3. Let Gradle sync and build the project

4. Run the app on an Android emulator or physical device (API level 21+)

## 🧪 Testing

- The project includes `test/` and `androidTest/` directories for unit and UI testing

## 📄 License

This project is open-source and intended for learning and demonstration purposes. You are free to modify and extend it as needed.
