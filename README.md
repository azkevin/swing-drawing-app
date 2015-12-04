# Swing-Drawing-App

A few comments:

First, after some investigation and discussion with a friend about how the undo/redo mechanism should work, it seems that saving the actions should be the best course of action to minimize memory usage. 

So with that, we should save the shapes into a stack, and call the respective method on that shape. (assuming that is what is already done)
 
 - Fill will use a Point2D.Double.
 - Text will also use Point2D.Double. 
 (need a way to differentiate between both, will leave it up to you)
 
 Secondly, it seems that the the drawing of the shapes has been detached from Graphics2D actually drawing on the canvas. This will cause issues for the fill + selection mechanisms.
 
 So I propose that, while preserving the current redo/undo mechanism, (assuming that it is just a stack of shapes created) that we simply just have a copy of the image saved as a BufferedImage, before any edits occur. We will refer to this as the original image. What we will be operating on is a separate BufferedImage called the canvas.
 
 So, assuming we have 2 stacks to deal with initially:
 	- the "history", which is a stack of all shapes created up until "now"
 	- the "undo" stack, which is a stack which contains what actions have been undone. (this stack will empty itself if we perform a new action)
 	
 So, when we undo an action, we simply take the original image, and create a copy of it and define it as the new canvas. Then, we will use an iterator, and iterate through all the actions of the history stack, and essentially repeat all the actions done in the history on the image.
 
When we are considering layers:

 - We will have to keep track of which layer an action was performed on.
 - The list of layers will contain a stack of the "history" of actions done on that layer
 - When undoing an action, we will pop the action from the overall history stack and push it onto the undo stack.
 - This will also pop the same action from the respective stack from the layer list.
 - We will then proceed to redraw that specific layer through iterating through the stack of the respective layer.
	
When considering filled shapes, a good solution would be to have a drop down list, with the options, no fill, or fill. A toggleable button would be sufficient as well. 
	
Having a no fill option, over selecting a white fill would synergize very well when considering layers.
 
Fill has already been implemented, but there is a certain bug with it.
I will fix it when I wake up. It is caused by attempting to fill when the target colour and the fill colour are the same, resulting in an infinite loop.

Text can be easily implemented.

Selection will require the involvement of a second layer, so we will ultimately end up implementing layers of some type regardless.