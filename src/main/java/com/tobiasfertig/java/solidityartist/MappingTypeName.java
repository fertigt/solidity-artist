package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

public class MappingTypeName extends TypeName
{
	private TypeName keyType;
	private TypeName valueType;

	public MappingTypeName( TypeName keyType, TypeName valueType )
	{
		super( Keywords.MAPPING.toString( ) );
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
