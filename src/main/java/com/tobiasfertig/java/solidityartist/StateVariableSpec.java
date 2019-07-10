package com.tobiasfertig.java.solidityartist;

public class StateVariableSpec implements Writable
{
	private TypeName type;
	private VisibilityName visibility;
	private ModifierName constant;
	private String name;
	private String initialValue;

	private StateVariableSpec( Builder builder )
	{
		this.type = builder.type;
		this.visibility = builder.visibility;
		this.constant = builder.constant ? ModifierName.CONSTANT : null;
		this.name = builder.name;
		this.initialValue = builder.initialValue;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( this.type )
			  .space( )
			  .write( this.visibility )
		      .space( );

		if(constant != null)
		{
			writer.write( constant )
				  .space( );
		}

		writer.write( name );

		if ( !initialValue.isEmpty( ) )
		{
			writer.space( )
				  .write( "=" )
				  .space( )
				  .write( initialValue );
		}

		writer.semicolon( );
	}

	public static Builder builder( TypeName type, String name )
	{
		return new Builder( type, name );
	}

	public static final class Builder
	{
		private final TypeName type;
		private final String name;
		private VisibilityName visibility;
		private boolean constant;
		private String initialValue;

		private Builder( TypeName type, String name )
		{
			this.type = type;
			this.name = name;
			this.constant = false;
			this.visibility = VisibilityName.PRIVATE;
			this.initialValue = "";
		}

		public Builder addVisibility( VisibilityName visibility )
		{
			this.visibility = visibility;
			return this;
		}

		public Builder isConstant( )
		{
			this.constant = true;
			return this;
		}

		public Builder addInitialValue( String initialValue )
		{
			this.initialValue = initialValue;
			return this;
		}

		public StateVariableSpec build( )
		{
			return new StateVariableSpec( this );
		}
	}
}
