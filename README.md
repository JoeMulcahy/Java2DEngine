Hello!

This is my first Java repo on GITHUB
I've been working on a java based 2d game editor that started off as an exercise on getting familiar with the Java Swing library for creating GUIs
Its still early days but I'm having a lot of fun coding!

The end goal of this project is to create a 2d game designer program that can create basic 2d games. Think pong and breakout

As of 06/10/2023
I have created basic shapes that can be drawn on a JPanel component. I've also implemented a grid so that shapes can snapped into position.

06/10/2023
Create a GameObjectsPanel. An object can be selected from a JList and its attributes display of a side panel.
These attributes can be changed in the side panel, these changes are reflected in the Editor windows.
Its not visually appealing at the moment. Editing of the object attributes are done through JTextBoxes for the moment. Eventually these will be changed to dropdown menu [true, false], spinners for changing ogame objects attributes
This module still need a lot of work in terms of usability

7/10/23
Implemented an undo redo system using a stack data structure. 
For the moment this feature works on objects created in the Editor Window. Eventually I want it to work on any action performed in the application

