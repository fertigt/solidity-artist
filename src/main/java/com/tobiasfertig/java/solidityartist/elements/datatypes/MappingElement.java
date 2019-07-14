package com.tobiasfertig.java.solidityartist.elements.datatypes;

import com.tobiasfertig.java.solidityartist.utils.Keywords;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class MappingElement extends DataTypeElement
{
	private DataTypeElement keyType;
	private DataTypeElement valueType;

	public MappingElement( DataTypeElement keyType, DataTypeElement valueType )
	{
		super( Keywords.MAPPING.toString( ) );
		this.keyType = keyType;
		this.valueType = valueType;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public DataTypeElement getKeyType( )
	{
		return keyType;
	}

	public DataTypeElement getValueType( )
	{
		return valueType;
	}
}
