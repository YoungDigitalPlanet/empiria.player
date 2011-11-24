package eu.ydp.empiria.player.client.module.object.impl;


public class HTML5AudioImpl implements AudioImpl{

  public String getHTML(String src){
    return "<audio src='" + src + "' controls='true'>Audio not supported!</audio>";
  }
}
