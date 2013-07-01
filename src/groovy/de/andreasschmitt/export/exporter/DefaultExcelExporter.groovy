package de.andreasschmitt.export.exporter

import de.andreasschmitt.export.builder.ExcelBuilder
import jxl.write.WritableImage

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
                        //Create image and add title
                        if(data[0].image){
                            // add Title format
                            if(data[0].titleformat){
                                def titleformat=data[0].titleformat
                                format(name: "title"){
                                    font( name: titleformat.fontfamily
                                        , bold: titleformat.bold
                                        , size:titleformat["size"]
                                        , alignment:jxl.write.Alignment."$titleformat.alignment")
                                }
                            }
                            
                            sheet.addImage(
                                new WritableImage(data[0].beginColumn
                                                , data[0].beginRow
                                                , data[0].crossColumn 
                                                , data[0].crossRow
                                                , data[0].image)
                            )
                            sheet.setRowView(data[0].beginRow, data[0].height)    //height
                            sheet.setColumnView(data[0].beginColumn, data[0].width)  //width
                            
                            sheet.mergeCells( data[0].mergeColumn
                                            , data[0].mergeRow
                                            , data[0].mergeToColumn
                                            , data[0].mergeToRow)
                            // add Title
                            if(data[0].title){
                                sheet.mergeCells( data[0].mergeColumn
                                                , data[0].mergeRow+1
                                                , data[0].mergeToColumn
                                                , data[0].mergeToRow)
                                cell( row: data[0].beginRow+1
                                    , column: data[0].beginColumn
                                    , value: data[0].title
                                    , format: "title")
                                rowIndex =  1                                
                            }
                            if(data[0].beginRow==0){
                                rowIndex +=1
                            }
                            
                            data.remove(0)
                        }
						
						//Create header
						if(isHeaderEnabled){
							fields.eachWithIndex { field, index ->
								String value = getLabel(field)
								cell(row: rowIndex, column: index, value: value, format: "header")	
							}
							rowIndex += 1
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