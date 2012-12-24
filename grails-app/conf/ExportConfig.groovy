

class ExportConfig {

  static Map exporters = [excelExporter: "de.andreasschmitt.export.exporter.DefaultExcelExporter",
                          csvExporter: "de.andreasschmitt.export.exporter.DefaultCSVExporter",
                          xmlExporter: "de.andreasschmitt.export.exporter.DefaultXMLExporter",
                          pdfExporter: "de.andreasschmitt.export.exporter.DefaultPDFExporter",
                          odsExporter: "de.andreasschmitt.export.exporter.DefaultODSExporter",
                          rtfExporter: "de.andreasschmitt.export.exporter.DefaultRTFExporter"]
  
}