<h1 align="center">Lak</h1>
<p align="center"><i>Loud Ass Keyboard</i></p>
<hr><p align="center">
  <a href="http://hits.dwyl.io/RubbaBoy/Lak"><img alt="HitCount" src="http://hits.dwyl.io/RubbaBoy/Lak.svg" /></a>
  <img alt="Stars" src="https://img.shields.io/github/stars/RubbaBoy/Lak.svg?label=Stars&style=flat" />
  <a href="https://wakatime.com/badge/github/RubbaBoy/Lak"><img alt="Time Tracker" src="https://wakatime.com/badge/github/RubbaBoy/Lak.svg"/></a>
  <a href="https://github.com/RubbaBoy/Lak/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/RubbaBoy/Lak.svg"/></a>
  <a href="https://github.com/RubbaBoy/Lak/graphs/contributors"><img alt="GitHub contributors" src="https://img.shields.io/github/contributors/RubbaBoy/Lak"></a>
  <a href="https://github.com/RubbaBoy/Lak/blob/master/LICENSE.txt"><img src="https://img.shields.io/github/license/RubbaBoy/Lak.svg" alt="License"/></a>
  <a href="https://github.com/RubbaBoy/Lak/actions?query=workflow%3A%22Docker+Build%22"><img src="https://github.com/RubbaBoy/Lak/workflows/Docker%20Build/badge.svg" alt="Docker Build"/></a>
    <a href="https://hub.docker.com/repository/docker/rubbaboy/lak"><img src="https://byob.yarr.is/RubbaBoy/Lak/Lak" alt="Lak master docker"/></a>
  <a href="https://hub.docker.com/layers/rubbaboy/hs"><img src="https://img.shields.io/docker/pulls/rubbaboy/lak" alt="Docker Pulls"/></a>
</p>

Lak is a software/hardware project that solves the problem of my typing being too quiet. The hardware only cost around $270 to turn a rubber dome keyboard into a audio powerhouse, more information on the hardware may be found in [Hardware.md](Hardware.md). This is used in tandem with the project [LakWebsite](https://github.com/RubbaBoy/LakWebsite), a website for configuration that uses this repository's REST API.

The premise is a physical keyboard with an aux port on it, so when a key is pressed, a sound is emitted. This is ran on a Raspberry Pi to interface with pushbuttons on keyboard, and to make the keyboard self-contained. A website and REST API is used to locally configure what keys play what sounds, modulate sound variants, and record sounds.

## Features

- A plug and play [hardware keyboard](Hardware.md) that works with any machine
- Easy to use web interface to modulate and add sounds
- Auto-updating via docker
- Abstraction of Pi interfacing to allow for full local usage
- Saleable and documented codebase

## Installing

If you happen to spend the money and time making your own Lak, first time usage is easy as pi (Local installation is very possible but potentially coming soon).

Currently on every push, the docker build [rubbaboy/lak](https://hub.docker.com/repository/docker/rubbaboy/lak) is updated with the tag `rubbaboy/lak:branch-hash` to allow for unique builds every time.

```
// TODO
```

## Developing

Lak includes several Gradle modules for increased modularity (obviously). The following are the modules used

[:main](/) The root module, holding all the core code

[:pi-api](/pi-api) An API to interface with Raspberry Pi hardware components in an abstracted manner, so they can be implemented in a purely local manner later on

[:pi](/pi) An implementation of pi-api, using Raspberry Pi-specific libraries and binaries to control GPIO

[:dummy-pi](/dummy-pi) A dummy implementation of pi-api so tests may be run on local machines