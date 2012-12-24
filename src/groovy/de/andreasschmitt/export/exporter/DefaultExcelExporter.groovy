package de.andreasschmitt.export.exporter

import de.andreasschmitt.export.builder.ExcelBuilder

/**
 * @author Andreas Schmitt
 * 
 */
class DefaultExcelExporter extends AbstractExporter {

	protected void exportData(OutputStream outputStream, List data, List fields) throws ExportingException{
		try {
			def builder = new ExcelBuilder()
			
			// Enable/Disable header output
			boolean isHeaderEnabled = true
			if(getParameters().containsKey("header.enabled")){
				isHeaderEnabled = getParameters().get("header.enabled")
			}
			
			builder {
				workbook(outputStream: outputStream){
					sheet(name: getParameters().get("title") ?: "Export", widths: getParameters().get("column.widths")){
						//Default format
						format(name: "header"){
							font(name: "arial", bold: true)
						}
						
						int rowIndex = 0
						
						//Create header
						if(isHeaderEnabled){
							fields.eachWithIndex { field, index ->
								String value = getLabel(field)
								cell(row: rowIndex, column: index, value: value, format: "header")	
							}
							
							rowIndex = 1
						}
						
						//Rows
						data.eachWithIndex { object, k ->
							fields.eachWithIndex { field, i ->
								Object value = getValue(object, field)
								cell(row: k + rowIndex, column: i, value: value)
							}
						}
					}
				}
			}
			
			builder.write()
		}
		catch(Exception e){
			throw new ExportingException("Error during export", e)
		}
	}
	
}