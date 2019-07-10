package com.tobiasfertig.java.solidityartist;

import java.util.LinkedHashSet;
import java.util.Set;

public class ModifierSpec implements Writable
{
	public final static String MODIFIER_KEYWORD = "modifier";

	private final ModifierName modifierName;
	private final Set<ParameterSpec> parameters;

	public ModifierSpec( Builder builder )
	{
		this.modifierName = builder.modifierName;
		parameters = builder.parameters;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( MODIFIER_KEYWORD )
			  .space( )
			  .write( modifierName )
			  .openBraces( )
			  .writeParameters( parameters )
			  .closeBraces( )
			  .space( )
			  .openCurlyBraces( )
			  .writeAndIndent( "_;" )
			  .closeCurlyBraces( );
	}

	public ModifierName getModifierName( )
	{
		return this.modifierName;
	}

	public static Builder builder( ModifierName modifierName )
	{
		return new Builder( modifierName );
	}

	public static final class Builder
	{
		private final ModifierName modifierName;
		private final Set<ParameterSpec> parameters = new LinkedHashSet<>( );

		private Builder( ModifierName modifierName )
		{
			this.modifierName = modifierName;
		}

		public Builder addParameters( Iterable<ParameterSpec> parameters )
		{
			for ( ParameterSpec parameter : parameters )
			{
				this.parameters.add( parameter );
			}

			return this;
		}

		public Builder addParameter( ParameterSpec parameter )
		{
			this.parameters.add( parameter );
			return this;
		}

		public ModifierSpec build( )
		{
			return new ModifierSpec( this );
		}
	}
}
