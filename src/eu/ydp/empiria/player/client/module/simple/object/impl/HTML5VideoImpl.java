package eu.ydp.empiria.player.client.module.simple.object.impl;


public class HTML5VideoImpl implements VideoImpl{

  public String getHTML(String src){
    return "<video src='" + src + "' controls='true'>Video not supported!</video>";
  }
}
