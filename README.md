Helloooooo!

This is my first Java repo on GITHUB
I've been working on a java based 2d game editor that started off as an exercise on getting familiar with the Java Swing library for creating GUIs
Its still early days but I'm having a lot of fun coding!

The end goal of this project is to create a 2d game designer program that can create basic 2d games. Think pong and breakout

As of 06/10/2023
I have created basic shapes that can be drawn on a JPanel component. I've also implemented a grid so that shapes can snapped into position.

06/10/2023
Create a GameObjectAttributesPanel. A GameObject can be selected from a JList and its attributes display on a side panel.
These attributes can be changed in the side panel, these changes are reflected in the Editor window.
Its not visually appealing. Editing of the object attributes are done through JTextBoxes for the moment. Eventually these will be changed to dropdown menu [true, false], spinners for changing ogame objects attributes
This module still need a lot of work in terms of usability

7/10/23
Implemented an undo redo system using a stack data structure. 
For the moment this feature works on objects created in the Editor Window. Eventually I want it to work on any action performed in the application

8/10/23

Added the ability to rotate game objects in the ObjectArrtributesPanel.
Reworked how the gameObjects are drawn. Instead of drawing the objects directly using 
g.fillRect/fillOval etc a shape object is created instead using the java,awt.Rectangle class and java.awt.geom.Ellipse2D class. These classes have intersect methods which will make collision detection alot easier to implement down the line. AffineTransform can also be used on these classes, this was how rotation was implemented

9/10/23
Added the ability to save and load projects. The save feature is not entirely finished. Saved file names are auto generated for the moment. All actions in the Editor window are also saved so you can undo actions after a project has been loaded. I need to add normal save functionality and a save-as function.
The load functionality is working but needs to be tested. 
I also started a 'New Project' function but needs work

10/10/23

Made a few tweaks to how to the selector box is drawn, code looks nicer. 
Made tweaks to how gameObjects are drawn, the application runs a lot smoother and no more high cpu usage.
Added an object highlight function. Select an object from the Game objects panel and a red flashing box
surrounds the object. Works well but will have to re tweak as the highlighter doesn't take object rotation into
consideration.
Objects can now change color from the Object Attributes panel. Added a Color Palette object to make the changes
Changed the way some object are accessed by other objects by changing some static methods to instance methods. 
Graphics can be disabled from the menu, not sure if this serves a function yet, its there!
Right angle triangle GameObjects is now working! I will add more functionality to this later i.e. different types triangles
Might increase this to make polygons -> pentagon, hexagons etc

11/10/23
We now have the option to add borders to game objects. It work fine for rect and circle objects but poly is a little trickier.
Poly borders are working but not exactly how I intend it to work, will revisit
Borders can be toggle from the Tools Panel
GameObjects now have depth, an object can be draw over or under an existing GameObject
Added a delete GameObject function. this isn't working. I tried deleting the selected GameObject from the objects ArrayList but throws errors, must investigate further
Started a feature that can resize GameObject from the Attributes Panel



