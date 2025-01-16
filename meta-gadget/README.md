This README file contains information on the contents of the meta-gadget layer.

Please see the corresponding sections below for details.

# Patches

Please submit any patches against the meta-gadget layer to the GitHub page:

https://github.com/AmateurECE/meta-ethantwardy.git

# Table of Contents

  I. Adding the meta-gadget layer to your build
 II. What's a Gadget?

# I. Adding the meta-gadget layer to your build

Run 'bitbake-layers add-layer meta-gadget'

# II. What's a Gadget?

For the purposes of this repository, a gadget is a device that runs an
immutable distribution and performs a specific function. I have a number of
gadgets in my repertoire:

1. A PiHole gadget
1. A gadget that allows clients to stream music through my stereo over
   Bluetooth
1. A gadget that runs email for a small business
1. A backup gadget

All of these have their software and their applications, but they all also have
some specific software life-cycle considerations:

* They serve one purpose
* They target one hardware platform
* I want software updates to be periodic and holistic--not package by package
* I want vulnerability monitoring for the software
* These concerns are not related to the gadget's primary responsibility

This meta layer provides images and classes that make it easy to spin up new
gadgets.
