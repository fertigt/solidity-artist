package com.tobiasfertig.java.solidityartist;

import java.util.LinkedHashSet;
import java.util.Set;

public class FunctionSpec implements Writable
{
	public final static String FUNCTION_KEYWORD = "function";

	private final String name;
	private final VisibilityName visibility;
	private final Set<ModifierSpec> modifiers;

	private FunctionSpec( Builder builder )
	{
		this.name = ( builder.name == null ) ? "" : builder.name;
		this.visibility = builder.visibility;
		this.modifiers = builder.modifierSpecs;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( FUNCTION_KEYWORD )
			  .write( this.name )
			  .emptyBraces( )
			  .space( )
			  .write( visibility )
			  .space( );

		for ( ModifierSpec modifier : modifiers )
		{
			writer.write( modifier );
		}

		writer.emptyCurlyBraces( );
	}

	public static Builder builder( VisibilityName visibility )
	{
		return new Builder( visibility );
	}

	public static final class Builder
	{
		private String name;
		private final VisibilityName visibility;
		private final Set<ModifierSpec> modifierSpecs = new LinkedHashSet<>( );

		private Builder( VisibilityName visibility )
		{
			this.visibility = visibility;
		}

		public Builder addName( String name )
		{
			this.name = name;
			return this;
		}

		public Builder addModifierSpecs( Iterable<ModifierSpec> modifierSpecs )
		{
			for ( ModifierSpec modifierSpec : modifierSpecs )
			{
				this.modifierSpecs.add( modifierSpec );
			}

			return this;
		}

		public Builder addModifier( ModifierSpec modifierSpec )
		{
			this.modifierSpecs.add( modifierSpec );
			return this;
		}

		public FunctionSpec build( )
		{
			return new FunctionSpec( this );
		}
	}
}
