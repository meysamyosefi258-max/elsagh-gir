# Samsung Bilingual Keyboard - Android APK

یک کیبورد دو‌زبانه (فارسی و انگلیسی) برای گوشی‌های اندروید با طراحی سامسونگ و تم‌های مختلف.

## ✨ ویژگی‌ها

- ✅ **دو‌زبانه**: پشتیبانی کامل از فارسی و انگلیسی
- 🎨 **تم‌های مختلف**: Light، Dark و Blue
- 🔄 **تبدیل سریع زبان**: کلید مختصی برای تغییر بین زبان‌ها
- 📱 **طراحی سامسونگ**: رابط کاربری مطابق استاندارد Samsung
- 🎯 **بهینه شده**: عملکرد سریع و مصرف کم انرژی

## 📦 نصب و ساخت

### پیش‌نیازها:
- Android Studio نسخه 2022.1 یا بالاتر
- SDK مینیمم: API 24 (Android 7.0)
- Target SDK: API 33 (Android 13)

### مراحل ساخت:

1. **کلون کردن مخزن:**
```bash
git clone https://github.com/meysamyosefi258-max/samsung-bilingual-keyboard.git
cd samsung-bilingual-keyboard
```

2. **باز کردن در Android Studio:**
   - Android Studio را باز کنید
   - `File > Open` را انتخاب کنید
   - پوشه مخزن را انتخاب کنید

3. **ساخت APK:**
   - `Build > Build Bundle(s) / APK(s) > Build APK(s)` کلیک کنید
   - یا از ترمینال:
```bash
./gradlew assembleDebug
```

4. **نصب روی گوشی:**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 🚀 استفاده

### فعال کردن کیبورد:

1. تنظیمات > زبان و ورودی
2. کیبورد صفحه‌نمایش مجازی > Samsung Bilingual Keyboard
3. کیبورد پیش‌فرض را انتخاب کنید
4. دستورالعمل‌های برنامه را دنبال کنید

### تغییر تم:

1. برنامه Samsung Bilingual Keyboard را باز کنید
2. تم مورد نظرتان را انتخاب کنید
3. تم فعال خواهد شد

### تبدیل زبان:

- دکمه `EN/FA` را فشار دهید یا
- دکمه Space را فشار و نگاه دارید

## 📁 ساختار پروژه

```
samsung-bilingual-keyboard/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/maysam/samsungkeyboard/
│   │   │   │   ├── keyboard/
│   │   │   │   │   └── SamsungIMEService.java
│   │   │   │   └── MainActivity.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── values/
│   │   │   │   └── xml/
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## 🎨 تم‌های موجود

### Light Theme ☀️
- پس‌زمینه: سفید
- متن: سیاه
- بهترین برای روز

### Dark Theme 🌙
- پس‌زمینه: تیره
- متن: سفید
- بهترین برای شب

### Blue Theme 💙
- پس‌زمینه: آبی تیره
- متن: سفید
- شیک و حرفه‌ای

## 🐛 عیب‌یابی

### کیبورد فعال نمی‌شود:
- اطمینان دهید که برنامه نصب شده است
- به تنظیمات بروید و دوباره فعال کنید

### کاراکترهای فارسی نمایش داده نمی‌شوند:
- حافظه سریع تلفن را پاک کنید
- برنامه را دوباره نصب کنید

## 📞 تماس و پشتیبانی

- نام طراح: Maysam Khan
- ایمیل: meysamyosefi258@gmail.com
- GitHub: @meysamyosefi258-max

## 📄 لایسنس

این پروژه تحت لایسنس MIT منتشر شده است.

---

**نسخه:** 1.0  
**آخرین به‌روزرسانی:** 2026-09-10
