## The Pi Module

The Pi module is used for interfacing with the Raspberry Pi's GPIO ports in an abstracted manner, allowing for the ability to have no reliance on Raspberry Pi-related dependencies if required. This will allow for non-pi builds, for standalone software builds using dummy implementations of this module.

The main features this module handles is GPIO managing (Not accessed outside of the module), along with light and button handling, which can be implemented in something like a web interface in the future. The bulk of the GPIO interfacing is done via the [pi4j](https://pi4j.com/1.2/index.html) library.