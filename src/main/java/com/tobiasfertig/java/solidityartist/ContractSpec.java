package com.tobiasfertig.java.solidityartist;

import java.util.LinkedHashSet;
import java.util.Set;

public class ContractSpec implements Writable
{
	public final static String CONTRACT_KEYWORD = "contract";
	public final static String PRAGMA_KEYWORD = "pragma solidity";
	public final static String IS_KEYWORD = "is";

	private final String pragma;
	private final Set<ImportSpec> importStatements;
	private final String name;
	private final Set<String> superContracts;
	private final Set<UsingForSpec> usingForSpecs;
	private final Set<StructTypeName> structDeclarations;
	private final Set<StateVariableSpec> stateVariables;
	private final Set<EventSpec> events;
	private final ConstructorSpec constructor;
	private final FunctionSpec fallbackFunction;
	private final Set<ModifierSpec> customModifiers;
	private final Set<FunctionSpec> functions;

	private ContractSpec( Builder builder )
	{
		this.pragma = builder.pragma;
		this.importStatements = builder.importStatements;
		this.name = builder.name;
		this.superContracts = builder.superContracts;
		this.usingForSpecs = builder.usingForSpecs;
		this.structDeclarations = builder.structDeclarations;
		this.stateVariables = builder.stateVariables;
		this.events = builder.events;
		this.constructor = builder.constructorSpec;
		this.fallbackFunction = builder.fallbackFunctionSpec;
		this.customModifiers = builder.customModifiers;
		this.functions = builder.functions;
	}

	private boolean hasImportStatements( )
	{
		return !importStatements.isEmpty( );
	}

	private boolean hasSuperContracts( )
	{
		return !superContracts.isEmpty( );
	}

	private boolean hasUsingForSpecs( )
	{
		return !usingForSpecs.isEmpty( );
	}

	private boolean hasStructDeclarations( )
	{
		return !structDeclarations.isEmpty( );
	}

	private boolean hasStateVariables( )
	{
		return !stateVariables.isEmpty( );
	}

	private boolean hasConstructor( )
	{
		return this.constructor != null;
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
			  .semicolonAndNewline( )
			  .newline( );

		if ( hasImportStatements( ) )
		{
			for ( ImportSpec importStatement : importStatements )
			{
				writer.write( importStatement );
			}

			writer.newline( );
		}

		writer.write( CONTRACT_KEYWORD )
			  .space( )
			  .write( this.name )
			  .space( );

		if ( hasSuperContracts( ) )
		{
			writer.write( IS_KEYWORD )
				  .space( )
				  .write( superContracts )
				  .space( );
		}

		writer.openCurlyBraces( );

		if ( hasUsingForSpecs( ) )
		{
			for ( UsingForSpec usingForSpec : usingForSpecs )
			{
				writer.write( usingForSpec );
			}

			writer.newline( );
		}

		if ( hasStructDeclarations( ) )
		{
			for ( StructTypeName structDeclaration : structDeclarations )
			{
				writer.write( structDeclaration )
					  .newline( )
					  .newline( );
			}
		}

		if ( hasStateVariables( ) )
		{
			for ( StateVariableSpec stateVariable : stateVariables )
			{
				writer.write( stateVariable );
			}

			writer.newline( );
		}

		if ( hasEvents( ) )
		{
			for ( EventSpec event : events )
			{
				writer.write( event );
			}

			writer.newline( );
		}

		if ( hasConstructor( ) )
		{
			writer.write( constructor )
				  .newline( )
				  .newline( );
		}

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
					  .newline( )
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
		private final Set<ImportSpec> importStatements = new LinkedHashSet<>( );
		private final String name;
		private final Set<String> superContracts = new LinkedHashSet<>( );
		private final Set<UsingForSpec> usingForSpecs = new LinkedHashSet<>( );
		private final Set<StructTypeName> structDeclarations = new LinkedHashSet<>( );
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

		public Builder addImportStatement( ImportSpec importStatement )
		{
			this.importStatements.add( importStatement );
			return this;
		}

		public Builder addSuperContract( String name )
		{
			this.superContracts.add( name );
			return this;
		}

		public Builder addSuperContract( ContractSpec superContract )
		{
			this.superContracts.add( superContract.name );
			return this;
		}

		public Builder addUsingForSpec( UsingForSpec usingForSpec )
		{
			this.usingForSpecs.add( usingForSpec );
			return this;
		}

		public Builder addStructDeclaration( StructTypeName structTypeName )
		{
			this.structDeclarations.add( structTypeName );
			return this;
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
