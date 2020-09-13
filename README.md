# Android Clean Architecture + MVP Sample
<p align="center">
<img src="https://raw.githubusercontent.com/ImangazalievM/UTair-MVP-Sample/master/assets/splash.png" />
</p>

The sample app that demonstrates using Clean Architecture + MVP.

The project contains 3 modules:
- **core** - contains common code for both modules
- **app-coroutines** - Coroutines, Dagger 2
- **app-rxjava** - RxJava 2, Toothpick

This project includes the following libraries, tools and solutions:

**[Dagger 2](https://github.com/google/dagger)** / **[Toothpick](https://github.com/stephanenicolas/toothpick)** - for dependency injection
**[RxJava 2](https://github.com/ReactiveX/RxJava)** / **[Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)** - for multithreading
**[Moxy](https://github.com/Arello-Mobile/Moxy)** - for MVP pattern implementation
**[Alligator](https://github.com/aartikov/Alligator)** - for screens navigatio
**[Spek](https://github.com/spekframework/spek)** + **MocKk** - for unit-tests
**[Kaspresso](https://github.com/KasperskyLab/Kaspresso)** - for UI-tests

## 🤝 License
```
The MIT License

Copyright (c) 2020 Mahach Imangazaliev

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```