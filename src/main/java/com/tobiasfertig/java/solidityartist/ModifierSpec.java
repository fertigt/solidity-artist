package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

import java.util.LinkedHashSet;
import java.util.Set;

public class ModifierSpec implements Writable
{
	private final ModifierName modifierName;
	private final Set<ParameterSpec> parameters;
	private final CodeBlock code;

	public ModifierSpec( Builder builder )
	{
		this.modifierName = builder.modifierName;
		this.parameters = builder.parameters;
		this.code = (builder.code != null) ? builder.code : CodeBlock.emptyBlock( );
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( Keywords.MODIFIER )
			  .space( )
			  .write( modifierName )
			  .openBraces( )
			  .writeParameters( parameters )
			  .closeBraces( )
			  .space( )
			  .openCurlyBraces( );

		if(!code.isEmpty())
		{
			writer.write( code )
				  .newline( );
		}

		writer.writeAndIndent( "_;" )
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
		private CodeBlock code;

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

		public Builder addCode( CodeBlock code )
		{
			this.code = code;
			return this;
		}

		public ModifierSpec build( )
		{
			return new ModifierSpec( this );
		}
	}
}
