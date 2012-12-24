package de.andreasschmitt.export.taglib.util

import java.rmi.server.UID
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Formatter
import org.apache.commons.codec.digest.DigestUtils
import java.util.regex.*

/**
 * @author Andreas Schmitt
 * 
 */
class RenderUtils {

	/**
	 * Create unique ID as hex representation.
	 */
	public static String getUniqueId() {
		return DigestUtils.md5Hex(new UID().toString())
    }
	
	/**
	 * 
	 * @param pluginName
	 * @param contextPath
	 * 
	 */
	public static String getResourcePath(String pluginName, String contextPath){
		//TODO find a more efficient way to retrieve plugin version getPlugin(name).version did not work 
		//def plugin = PluginManagerHolder?.pluginManager?.allPlugins.find { it.name == pluginName.toLowerCase() }
		//String pluginVersion = plugin?.version
				
		//The above version doesn't work on Jetty so for now an ugly approach will be used
		String pluginVersion = "1.5"
		
		"${contextPath}/plugins/${pluginName.toLowerCase()}-$pluginVersion"
	}
	
	/**
	 * 
	 * @param pluginResourcePath 
	 * 
	 */
	public static String getApplicationResourcePath(String pluginResourcePath){
		try {
			Pattern pattern = Pattern.compile("(.*)/plugins.*");
			Matcher matcher = pattern.matcher(pluginResourcePath);
			
			if(matcher.matches()){
				return matcher.group(1);
			}
		}
		catch(Exception e){
			return ""
		}
	}

}