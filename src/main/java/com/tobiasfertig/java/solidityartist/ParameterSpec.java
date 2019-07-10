package com.tobiasfertig.java.solidityartist;

public class ParameterSpec implements Writable
{
	public final static String RETURN_PARAMETER_KEYWORD = "returns";

	private TypeName type;
	private LocationName location;
	private String name;

	private ParameterSpec( Builder builder )
	{
		this.type = builder.type;
		this.location = builder.location;
		this.name = builder.name;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.write( type );

		if ( location != null )
		{
			writer.space( )
				  .write( location );
		}

		if ( name != null )
		{
			writer.space( )
				  .write( name );
		}
	}

	public static Builder builder( TypeName type )
	{
		return new Builder( type );
	}

	public static final class Builder
	{
		private final TypeName type;
		private LocationName location;
		private String name;

		private Builder( TypeName type )
		{
			this.type = type;
		}

		public Builder addLocation( LocationName location )
		{
			this.location = location;
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
