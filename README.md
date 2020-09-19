# Android Clean Architecture + MVP Sample
<p align="center">
<img src="https://raw.githubusercontent.com/ImangazalievM/UTair-MVP-Sample/master/assets/splash.png" />
</p>

The sample app that demonstrates using Clean Architecture + MVP.

### 🏛 Project Structure

The project contains 3 modules:
- **core** - contains common code for both modules
- **app-coroutines** - Coroutines, Toothpick
- **app-rxjava** - RxJava 2, Dagger 2

This project includes the following libraries, tools and solutions:

- [Clean Architecture](https://github.com/ImangazalievM/CleanArchitectureManifest)
- [Dagger 2](https://github.com/google/dagger) / [Toothpick](https://github.com/stephanenicolas/toothpick) - for dependency injection
- [RxJava 2](https://github.com/ReactiveX/RxJava) / [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - for multithreading
- [Moxy](https://github.com/Arello-Mobile/Moxy) - for MVP pattern implementation
- [Alligator](https://github.com/aartikov/Alligator) - for screens navigatio
- [Spek](https://github.com/spekframework/spek) + [MockK](https://github.com/mockk/mockk) + [Strikt](https://github.com/robfletcher/strikt/) - for unit-tests
- [Kaspresso](https://github.com/KasperskyLab/Kaspresso) - for UI-tests
- Gradle Kotlin DSL

### 🌦 Open Weather API

The app uses [OpenWeather API](https://openweathermap.org/api) for receiving weather forecasts, 
so to build the project you have to provide API key. To do it create account on OpenWeather website,
 then generate your own API key and put it to `open_weather_api_key` property in `api_keys.properties` file.

### ⚠ Attention:
Clean Architecture approach [recommends](https://github.com/ImangazalievM/CleanArchitectureManifest#repository) us to create interfaces for repositories, so domain layer shouldn't know anything about data layer. The main goal of this rule is ability to test our interactors using simple unit-tests. IMHO, it is redundant because using repositories implementation directly doesn't cause any problems.

### 🚦️ Tests Runnning

UI and unit-tests are contained in the **app-coroutines** module.

To run unit-tests, you need to setup [Spek Framework](https://plugins.jetbrains.com/plugin/10915-spek-framework) plugin.

## 🤝 License
```
The MIT License

Copyright (c) 2020 Mahach Imangazaliev

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```