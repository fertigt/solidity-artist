package com.tobiasfertig.java.solidityartist;

import java.util.LinkedHashSet;
import java.util.Set;

public class ContractSpec implements Writable
{
	public final static String CONTRACT_KEYWORD = "contract";
	public final static String PRAGMA_KEYWORD = "pragma solidity";

	private final String pragma;
	private final String name;
	private final ConstructorSpec constructor;
	private final FunctionSpec fallbackFunction;
	private final Set<ModifierSpec> customModifiers;

	private ContractSpec( Builder builder )
	{
		this.pragma = builder.pragma;
		this.name = builder.name;
		this.constructor = builder.constructorSpec;
		this.fallbackFunction = builder.fallbackFunctionSpec;
		this.customModifiers = builder.customModifiers;
	}

	private boolean hasFallbackFunction( )
	{
		return fallbackFunction != null;
	}

	private boolean hasCustomModifiers( )
	{
		return !customModifiers.isEmpty( );
	}

	public void write( CodeWriter writer )
	{
		writer.write( PRAGMA_KEYWORD )
			  .space( )
			  .write( this.pragma )
			  .semicolon( )
			  .newline( )
			  .newline( )
			  .write( CONTRACT_KEYWORD )
			  .space( )
			  .write( this.name )
			  .space( )
			  .openCurlyBraces( )
			  .write( constructor )
			  .newline( )
			  .newline( );

		if ( hasFallbackFunction( ) )
		{
			writer.write( fallbackFunction )
				  .newline( )
				  .newline( );
		}

		if ( hasCustomModifiers( ) )
		{
			for ( ModifierSpec customModifier : customModifiers )
			{
				writer.write( customModifier )
					  .newline( );
			}
		}

		writer.closeCurlyBraces( );
	}

	public static Builder builder( String pragma, String name )
	{
		return new Builder( pragma, name );
	}

	public static final class Builder
	{
		private final String pragma;
		private final String name;
		private ConstructorSpec constructorSpec;
		private FunctionSpec fallbackFunctionSpec;
		private final Set<ModifierSpec> customModifiers = new LinkedHashSet<>( );

		private Builder( String pragma, String name )
		{
			this.pragma = pragma;
			this.name = name;
		}

		public Builder addConstructor( ConstructorSpec constructorSpec )
		{
			this.constructorSpec = constructorSpec;
			return this;
		}

		public Builder addFallbackFunction( FunctionSpec fallbackFunctionSpec )
		{
			this.fallbackFunctionSpec = fallbackFunctionSpec;
			return this;
		}

		public Builder addCustomModifier( ModifierSpec modifierSpec )
		{
			this.customModifiers.add( modifierSpec );
			return this;
		}

		public ContractSpec build( )
		{
			return new ContractSpec( this );
		}
	}
}
