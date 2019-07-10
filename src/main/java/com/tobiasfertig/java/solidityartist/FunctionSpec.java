package com.tobiasfertig.java.solidityartist;

import java.util.LinkedHashSet;
import java.util.Set;

public class FunctionSpec implements Writable
{
	public final static String FUNCTION_KEYWORD = "function";

	private final String name;
	private final VisibilityName visibility;
	private final Set<ModifierName> modifiers;
	private final Set<ModifierSpec> customModifiers;

	private FunctionSpec( Builder builder )
	{
		this.name = ( builder.name == null ) ? "" : builder.name;
		this.visibility = builder.visibility;
		this.modifiers = builder.modifiers;
		this.customModifiers = builder.customModifierSpecs;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( FUNCTION_KEYWORD )
			  .write( this.name )
			  .emptyBraces( )
			  .space( )
			  .write( visibility )
			  .space( );

		for ( ModifierName modifier : modifiers )
		{
			writer.write( modifier )
				  .space( );
		}

		for ( ModifierSpec modifier : customModifiers )
		{
			writer.write( modifier )
				  .space( );
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
		private final Set<ModifierName> modifiers = new LinkedHashSet<>( );
		private final Set<ModifierSpec> customModifierSpecs = new LinkedHashSet<>( );

		private Builder( VisibilityName visibility )
		{
			this.visibility = visibility;
		}

		public Builder addName( String name )
		{
			this.name = name;
			return this;
		}

		public Builder addModifierNames( Iterable<ModifierName> modifierNames )
		{
			for ( ModifierName modifierName : modifierNames )
			{
				this.modifiers.add( modifierName );
			}

			return this;
		}

		public Builder addModifierName( ModifierName modifierName )
		{
			this.modifiers.add( modifierName );
			return this;
		}

		public Builder addCustomModifierSpecs( Iterable<ModifierSpec> customModifierSpecs )
		{
			for ( ModifierSpec modifierSpec : customModifierSpecs )
			{
				this.customModifierSpecs.add( modifierSpec );
			}

			return this;
		}

		public Builder addCustomModifierSpec( ModifierSpec customModifierSpec )
		{
			this.customModifierSpecs.add( customModifierSpec );
			return this;
		}

		public FunctionSpec build( )
		{
			return new FunctionSpec( this );
		}
	}
}
