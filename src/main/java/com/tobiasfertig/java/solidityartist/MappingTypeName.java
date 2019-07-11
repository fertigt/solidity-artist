package com.tobiasfertig.java.solidityartist;

public class MappingTypeName extends TypeName
{
	public final static String MAPPING_KEYWORD = "mapping";

	private TypeName keyType;
	private TypeName valueType;

	public MappingTypeName( TypeName keyType, TypeName valueType )
	{
		super( MAPPING_KEYWORD );
		this.keyType = keyType;
		this.valueType = valueType;
	}

	@Override public void write( CodeWriter writer )
	{
		super.write( writer );
		writer.openBraces( )
			  .write( keyType )
			  .space( )
			  .write( "=>" )
			  .space( )
			  .write( valueType )
			  .closeBraces( );
	}
}
