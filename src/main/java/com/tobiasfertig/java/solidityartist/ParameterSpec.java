package com.tobiasfertig.java.solidityartist;

public class ParameterSpec implements Writable
{
	public final static String RETURN_PARAMETER_KEYWORD = "returns";

	private final TypeName type;
	private final AttributeName attribute;
	private final String name;

	private ParameterSpec( Builder builder )
	{
		this.type = builder.type;
		this.attribute = builder.attribute;
		this.name = builder.name;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.write( this.type );

		if ( this.attribute != null )
		{
			writer.space( )
				  .write( this.attribute );
		}

		if ( this.name != null )
		{
			writer.space( )
				  .write( this.name );
		}
	}

	public TypeName getType( )
	{
		return type;
	}

	public static Builder builder( TypeName type )
	{
		return new Builder( type );
	}

	public static final class Builder
	{
		private final TypeName type;
		private AttributeName attribute;
		private String name;

		private Builder( TypeName type )
		{
			this.type = type;
		}

		public Builder addLocation( LocationName location )
		{
			this.attribute = location;
			return this;
		}

		public Builder addEventParameterAttribute( EventParameterAttributeName attribute )
		{
			this.attribute = attribute;
			return this;
		}

		public Builder addName( String name )
		{
			this.name = name;
			return this;
		}

		public ParameterSpec build( )
		{
			return new ParameterSpec( this );
		}
	}
}
