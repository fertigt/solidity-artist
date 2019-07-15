package com.tobiasfertig.java.solidityartist.elements.statevariables;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.utils.Keyword;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

public class StateVariableElement implements SolidityElement
{
	private final DataTypeElement dataType;
	private final String visibility;
	private final boolean isConstant;
	private final String name;
	private final String initialization;

	private StateVariableElement( Builder builder )
	{
		this.dataType = builder.dataType;
		this.visibility = builder.visibility;
		this.isConstant = builder.isConstant;
		this.name = builder.name;
		this.initialization = builder.initialization;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public DataTypeElement getDataType( )
	{
		return dataType;
	}

	public String getVisibility( )
	{
		return visibility;
	}

	public boolean isConstant( )
	{
		return isConstant;
	}

	public String getName( )
	{
		return name;
	}

	public String getInitialization( )
	{
		return initialization;
	}

	public static Builder builder( DataTypeElement dataType, String name )
	{
		return new Builder( dataType, name );
	}

	public static final class Builder
	{
		private final DataTypeElement dataType;
		private final String name;
		private String visibility;
		private boolean isConstant;
		private String initialization;

		private Builder( DataTypeElement dataType, String name )
		{
			this.dataType = dataType;
			this.name = name;
			this.isConstant = false;
			this.visibility = Keyword.INTERNAL.toString( );
			this.initialization = "";
		}

		public Builder isExternal( )
		{
			this.visibility = Keyword.EXTERNAL.toString( );
			return this;
		}

		public Builder isInternal( )
		{
			this.visibility = Keyword.INTERNAL.toString( );
			return this;
		}

		public Builder isPrivate( )
		{
			this.visibility = Keyword.PRIVATE.toString( );
			return this;
		}

		public Builder isPublic( )
		{
			this.visibility = Keyword.PUBLIC.toString( );
			return this;
		}

		public Builder isConstant( )
		{
			this.isConstant = true;
			return this;
		}

		public Builder addInitialization( String initialization )
		{
			this.initialization = initialization;
			return this;
		}

		public StateVariableElement build( )
		{
			return new StateVariableElement( this );
		}
	}
}
