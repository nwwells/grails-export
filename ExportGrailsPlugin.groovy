import org.apache.commons.logging.LogFactory

class ExportGrailsPlugin {
    // the plugin version
    def version = "1.5"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Export Plugin" // Headline display name of the plugin
    def author = "Andreas Schmitt"
    def authorEmail = "andreas.schmitt.mi@gmail.com"
    def description = '''\
This plugin offers export functionality supporting different formats e.g. CSV, Excel, Open Document Spreadsheet, PDF and XML 
and can be extended to add additional formats. 
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/export"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {        
		//This is only necessary here, because later on log is injected by Spring
		def log = LogFactory.getLog(ExportGrailsPlugin)
		 
		"exporterFactory"(de.andreasschmitt.export.exporter.DefaultExporterFactory)
		
		try {				
			ExportConfig.exporters.each { key, value ->
		  		try {
		  			//Override default renderer configuration
					if(application.config?.export."${key}"){
						value = grailsApplication.config.export."${key}"
					}
		  			
		      		Class clazz = Class.forName(value, true, new GroovyClassLoader())
		      		
		      		//Add to spring
		      		"$key"(clazz) { bean ->
						  bean.scope = "prototype"
					}	
		  		}
		  		catch(ClassNotFoundException e){
		  			log.error("Couldn't find class: ${value}", e)
		  		}
			}			
		}
		catch(Exception e){
			log.error("Error initializing Export plugin", e)
		}
		catch(Error e){
			//Strange error which happens when using generate-all and hibernate.cfg
			log.error("Error initializing Export plugin")
		}
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
