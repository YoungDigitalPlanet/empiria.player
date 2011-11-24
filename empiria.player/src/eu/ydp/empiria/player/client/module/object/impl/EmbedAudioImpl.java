package eu.ydp.empiria.player.client.module.object.impl;



public class EmbedAudioImpl implements AudioImpl{

  public String getHTML(String src){

    return "<embed src='" + src + "' autostart='false' controller='true'></embed>";
  }
}
