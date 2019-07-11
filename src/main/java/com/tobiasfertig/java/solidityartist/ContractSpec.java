package com.tobiasfertig.java.solidityartist;

import java.util.LinkedHashSet;
import java.util.Set;

public class ContractSpec implements Writable
{
	public final static String CONTRACT_KEYWORD = "contract";
	public final static String PRAGMA_KEYWORD = "pragma solidity";

	private final String pragma;
	private final String name;
	private final Set<StateVariableSpec> stateVariables;
	private final Set<EventSpec> events;
	private final ConstructorSpec constructor;
	private final FunctionSpec fallbackFunction;
	private final Set<ModifierSpec> customModifiers;
	private final Set<FunctionSpec> functions;

	private ContractSpec( Builder builder )
	{
		this.pragma = builder.pragma;
		this.name = builder.name;
		this.stateVariables = builder.stateVariables;
		this.events = builder.events;
		this.constructor = builder.constructorSpec;
		this.fallbackFunction = builder.fallbackFunctionSpec;
		this.customModifiers = builder.customModifiers;
		this.functions = builder.functions;
	}

	private boolean hasStateVariables( )
	{
		return !stateVariables.isEmpty( );
	}

	private boolean hasEvents( )
	{
		return !events.isEmpty( );
	}

	private boolean hasFallbackFunction( )
	{
		return fallbackFunction != null;
	}

	private boolean hasCustomModifiers( )
	{
		return !customModifiers.isEmpty( );
	}

	private boolean hasFunctions( )
	{
		return !functions.isEmpty( );
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
			  .openCurlyBraces( );

		if ( hasStateVariables( ) )
		{
			for ( StateVariableSpec stateVariable : stateVariables )
			{
				writer.write( stateVariable );
			}
		}

		writer.newline( );

		if ( hasEvents( ) )
		{
			for ( EventSpec event : events )
			{
				writer.write( event );
			}
		}

		writer.newline( )
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
					  .newline( )
					  .newline( );
			}
		}

		if ( hasFunctions( ) )
		{
			for ( FunctionSpec function : functions )
			{
				writer.write( function )
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
		private final Set<StateVariableSpec> stateVariables = new LinkedHashSet<>( );
		private final Set<EventSpec> events = new LinkedHashSet<>( );
		private ConstructorSpec constructorSpec;
		private FunctionSpec fallbackFunctionSpec;
		private final Set<ModifierSpec> customModifiers = new LinkedHashSet<>( );
		private final Set<FunctionSpec> functions = new LinkedHashSet<>( );

		private Builder( String pragma, String name )
		{
			this.pragma = pragma;
			this.name = name;
		}

		public Builder addStateVariable( StateVariableSpec stateVariableSpec )
		{
			this.stateVariables.add( stateVariableSpec );
			return this;
		}

		public Builder addEvent( EventSpec eventSpec )
		{
			this.events.add( eventSpec );
			return this;
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

		public Builder addFunction( FunctionSpec functionSpec )
		{
			this.functions.add( functionSpec );
			return this;
		}

		public ContractSpec build( )
		{
			return new ContractSpec( this );
		}
	}
}
