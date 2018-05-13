/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.bottom;

import javafx.scene.control.TextArea;


/**
 *
 * @author kenzo
 */
public class Console extends TextArea
{

    public Console()
    {
        super();
        this.editableProperty().set(false);
    }

    public void printLine(String line)
    {
        this.appendText(line + '\n');
    }

    public void clearText(){
        this.clear();
        System.out.println("cleared");
    }
}
