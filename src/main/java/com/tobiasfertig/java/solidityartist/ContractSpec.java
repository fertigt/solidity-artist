package com.tobiasfertig.java.solidityartist;

public class ContractSpec implements Writable
{
	public final static String CONTRACT_KEYWORD = "contract";
	public final static String PRAGMA_KEYWORD = "pragma solidity";

	private String pragma;
	private String name;
	private ConstructorSpec constructor;
	private FunctionSpec fallbackFunction;

	private ContractSpec( Builder builder )
	{
		this.pragma = builder.pragma;
		this.name = builder.name;
		this.constructor = builder.constructorSpec;
		this.fallbackFunction = builder.fallbackFunctionSpec;
	}

	private boolean hasFallbackFunction( )
	{
		return fallbackFunction != null;
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
			writer.write( fallbackFunction );
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

		public ContractSpec build( )
		{
			return new ContractSpec( this );
		}
	}
}
