package com.qtitools.player.client.controller.style.test;

import junit.framework.Assert;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

import eu.ydp.empiria.player.client.util.js.JSOModel;

public class JSOModelTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.qtitools.player.Player";
	}

	public void testModelCreation() {
		JSOModel m = JavaScriptObject.createObject().cast();
		Assert.assertTrue( "JSOModel created", m instanceof JSOModel);
	}
	
	public void testModelProperties() {
		JSOModel m = JavaScriptObject.createObject().cast();
		m.set("color", "#FF0000");
		Assert.assertEquals("model returns correct property", "#FF0000", m.get("color") );
		Assert.assertEquals( "model returns \"undefined\" string for empty properties", "undefined", m.get("color2"));
	}
	
	public void testValidJSON() {
		JSOModel m = JSOModel.fromJson( "{\"color\": \"#FF0000\", \"customProperty\": \"abc\", \"intProperty\": 100}" );
		Assert.assertTrue( "JSOModel created", m instanceof JSOModel);
		Assert.assertEquals("model created from JSON returns correct property", "#FF0000", m.get("color") );
	}
	
}
