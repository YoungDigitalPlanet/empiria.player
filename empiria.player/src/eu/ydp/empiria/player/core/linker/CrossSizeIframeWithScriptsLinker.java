package eu.ydp.empiria.player.core.linker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedSet;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.CompilationResult;
import com.google.gwt.core.ext.linker.ConfigurationProperty;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.ScriptReference;
import com.google.gwt.core.ext.linker.Shardable;
import com.google.gwt.core.ext.linker.LinkerOrder.Order;
import com.google.gwt.core.ext.linker.impl.PropertiesUtil;
import com.google.gwt.core.ext.linker.impl.ResourceInjectionUtil;
import com.google.gwt.core.linker.CrossSiteIframeLinker;

@LinkerOrder(Order.PRIMARY)
@Shardable
public class CrossSizeIframeWithScriptsLinker extends CrossSiteIframeLinker {

	  @Override
	  protected String fillSelectionScriptTemplate(StringBuffer ss, TreeLogger logger,
	      LinkerContext context, ArtifactSet artifacts, CompilationResult result)
	      throws UnableToCompleteException {

	    if (shouldUseSelfForWindowAndDocument(context)) {
	      replaceAll(ss, "__WINDOW_DEF__", "self");
	      replaceAll(ss, "__DOCUMENT_DEF__", "self");
	    } else {
	      replaceAll(ss, "__WINDOW_DEF__", "window");
	      replaceAll(ss, "__DOCUMENT_DEF__", "document");
	    }

	    // Must do installScript before installLocation and waitForBodyLoaded
	    includeJs(ss, logger, getJsInstallScript(context), "__INSTALL_SCRIPT__");
	    includeJs(ss, logger, getJsInstallLocation(context), "__INSTALL_LOCATION__");

	    // Must do waitForBodyLoaded before isBodyLoaded
	    includeJs(ss, logger, getJsWaitForBodyLoaded(context), "__WAIT_FOR_BODY_LOADED__");
	    includeJs(ss, logger, getJsIsBodyLoaded(context), "__IS_BODY_LOADED__");

	    // Must do permutations before providers
	    includeJs(ss, logger, getJsPermutations(context), "__PERMUTATIONS__");
	    includeJs(ss, logger, getJsProperties(context), "__PROPERTIES__");
	    includeJs(ss, logger, getJsProcessMetas(context), "__PROCESS_METAS__");
	    includeJs(ss, logger, getJsComputeScriptBase(context), "__COMPUTE_SCRIPT_BASE__");
	    includeJs(ss, logger, getJsComputeUrlForResource(context), "__COMPUTE_URL_FOR_RESOURCE__");
	    includeJs(ss, logger, getJsLoadExternalStylesheets(context), "__LOAD_STYLESHEETS__");

	    // This Linker does support <script> tags in the gwt.xml rrybacki@ydp.com.pl
	    SortedSet<ScriptReference> scripts = artifacts.find(ScriptReference.class);
	    System.out.println("Linking " + scripts.size() + " external scripts:" );
	    for (ScriptReference scriptRef : scripts){
		    System.out.println("\t" + scriptRef.getSrc()); 
	    }
	    	    		
	    StringBuffer scriptsString = new StringBuffer();
		for (ScriptReference resource : artifacts.find(ScriptReference.class)) {
			String text = generateScriptInjector(resource.getSrc());
			scriptsString.append(text);
		}

	    includeJs(ss, logger, scriptsString.toString(), "__LOAD_SCRIPTS__");
	    
	    
	    ss = ResourceInjectionUtil.injectStylesheets(ss, artifacts);
	    ss = permutationsUtil.addPermutationsJs(ss, logger, context);

	    if (result != null) {
	      replaceAll(ss, "__KNOWN_PROPERTIES__", PropertiesUtil.addKnownPropertiesJs(logger, result));
	    }
	    replaceAll(ss, "__MODULE_FUNC__", context.getModuleFunctionName());
	    replaceAll(ss, "__MODULE_NAME__", context.getModuleName());
	    replaceAll(ss, "__HOSTED_FILENAME__", getHostedFilenameFull(context));

	    if (context.isOutputCompact()) {
	      replaceAll(ss, "__START_OBFUSCATED_ONLY__", "");
	      replaceAll(ss, "__END_OBFUSCATED_ONLY__", "");
	    } else {
	      replaceAll(ss, "__START_OBFUSCATED_ONLY__", "/*");
	      replaceAll(ss, "__END_OBFUSCATED_ONLY__", "*/");
	    }

	    String jsModuleFunctionErrorCatch = getJsModuleFunctionErrorCatch(context);
	    if (jsModuleFunctionErrorCatch != null) {
	      replaceAll(ss, "__BEGIN_TRY_BLOCK__", "try {");
	      replaceAll(ss, "__END_TRY_BLOCK_AND_START_CATCH__", "} catch (moduleError) {");
	      includeJs(ss, logger, jsModuleFunctionErrorCatch, "__MODULE_FUNC_ERROR_CATCH__");
	      replaceAll(ss, "__END_CATCH_BLOCK__", "}");
	    } else {
	      replaceAll(ss, "__BEGIN_TRY_BLOCK__", "");
	      replaceAll(ss, "__END_TRY_BLOCK_AND_START_CATCH__", "");
	      replaceAll(ss, "__MODULE_FUNC_ERROR_CATCH__", "");
	      replaceAll(ss, "__END_CATCH_BLOCK__", "");
	    }

	    return ss.toString();
	  }
	  

	  private static String generateScriptInjector(String scriptUrl) {
		    if (isRelativeURL(scriptUrl)) {
		      return "  if (!__gwt_scriptsLoaded['"
		          + scriptUrl
		          + "']) {\n"
		          + "    __gwt_scriptsLoaded['"
		          + scriptUrl
		          + "'] = true;\n"
		          + "    document.write('<script language=\\\"javascript\\\" src=\\\"'+computeScriptBase()+'"
		          + scriptUrl + "\\\"></script>');\n" + "  }\n";
		    } else {
		      return "  if (!__gwt_scriptsLoaded['" + scriptUrl + "']) {\n"
		          + "    __gwt_scriptsLoaded['" + scriptUrl + "'] = true;\n"
		          + "    document.write('<script language=\\\"javascript\\\" src=\\\""
		          + scriptUrl + "\\\"></script>');\n" + "  }\n";
		    }
		  }
	  
	  /**
	   * Determines whether or not the URL is relative.
	   * 
	   * @param src the test url
	   * @return <code>true</code> if the URL is relative, <code>false</code> if not
	   */
	  private static boolean isRelativeURL(String src) {
	    // A straight absolute url for the same domain, server, and protocol.
	    if (src.startsWith("/")) {
	      return false;
	    }

	    // If it can be parsed as a URL, then it's probably absolute.
	    try {
	      // Just check to see if it can be parsed, no need to store the result.
	      new URL(src);

	      // Let's guess that it is absolute (thus, not relative).
	      return false;
	    } catch (MalformedURLException e) {
	      // Do nothing, since it was a speculative parse.
	    }

	    // Since none of the above matched, let's guess that it's relative.
	    return true;
	  }
	  

	  @Override
	  protected String getSelectionScriptTemplate(TreeLogger logger, LinkerContext context) {
		  return "eu/ydp/empiria/player/core/linker/CrossSiteIframeWithScriptsTemplate.js";
	  }
}
