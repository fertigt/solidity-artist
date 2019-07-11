package com.tobiasfertig.java.solidityartist;

import java.util.LinkedHashSet;
import java.util.Set;

public class ConstructorSpec implements Writable
{
	public final static String CONSTRUCTOR_KEYWORD = "constructor";

	private final VisibilityName visibility;
	private final Set<ModifierName> modifiers;
	private final Set<ModifierName> customModifiers;
	private final CodeBlock code;

	private ConstructorSpec( Builder builder )
	{
		this.visibility = builder.visibility;
		this.modifiers = builder.modifiers;
		this.customModifiers = builder.customModifiers;
		this.code = (builder.code != null) ? builder.code : CodeBlock.emptyBlock( );
	}

	public void write( CodeWriter writer )
	{
		writer.writeAndIndent( CONSTRUCTOR_KEYWORD )
			  .emptyBraces( )
			  .space( )
			  .write( this.visibility )
			  .space( );

		for ( ModifierName modifier : modifiers )
		{
			writer.write( modifier )
				  .space( );
		}

		for ( ModifierName customModifier : customModifiers )
		{
			writer.write( customModifier )
				  .space( );
		}

		if ( code.isEmpty( ) )
		{
			writer.emptyCurlyBraces( );
		} else
		{
			writer.openCurlyBraces( )
				  .write( code )
				  .closeCurlyBraces( );
		}
	}

	public static Builder builder( VisibilityName visibility )
	{
		return new Builder( visibility );
	}

	public static final class Builder
	{
		private final VisibilityName visibility;
		private final Set<ModifierName> modifiers = new LinkedHashSet<>( );
		private final Set<ModifierName> customModifiers = new LinkedHashSet<>( );
		private CodeBlock code;

		private Builder( VisibilityName visibility )
		{
			this.visibility = visibility;
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
				this.customModifiers.add( modifierSpec.getModifierName( ) );
			}

			return this;
		}

		public Builder addCustomModifierSpec( ModifierSpec customModifierSpec )
		{
			this.customModifiers.add( customModifierSpec.getModifierName( ) );
			return this;
		}

		public Builder addCode( CodeBlock code )
		{
			this.code = code;
			return this;
		}

		public ConstructorSpec build( )
		{
			return new ConstructorSpec( this );
		}
	}
}
