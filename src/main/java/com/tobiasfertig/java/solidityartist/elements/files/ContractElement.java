package com.tobiasfertig.java.solidityartist.elements.files;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ConstructorElement;
import com.tobiasfertig.java.solidityartist.elements.functions.FunctionElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ModifierElement;
import com.tobiasfertig.java.solidityartist.elements.statevariables.StateVariableElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class ContractElement implements SolidityElement
{
	private final String name;
	private final Set<String> inheritedContracts;
	private final Set<UsingForElement> usingForDeclarations;
	private final Set<EnumElement> enumDeclarations;
	private final Set<StructElement> structDeclarations;
	private final Set<StateVariableElement> stateVariables;
	private final Set<EventElement> eventDeclarations;
	private final Set<ModifierElement> modifierDeclarations;
	private final ConstructorElement constructor;
	private final FunctionElement fallbackFunction;
	private final Set<FunctionElement> externalFunctions;
	private final Set<FunctionElement> publicFunctions;
	private final Set<FunctionElement> internalFunctions;
	private final Set<FunctionElement> privateFunctions;

	private ContractElement( Builder builder )
	{
		this.name = builder.name;
		this.inheritedContracts = builder.inheritedContracts;
		this.usingForDeclarations = builder.usingForDeclarations;
		this.enumDeclarations = builder.enumDeclarations;
		this.structDeclarations = builder.structDeclarations;
		this.stateVariables = builder.stateVariables;
		this.eventDeclarations = builder.eventDeclarations;
		this.modifierDeclarations = builder.modifierDeclarations;
		this.constructor = builder.constructor;
		this.fallbackFunction = builder.fallbackFunction;
		this.externalFunctions = builder.externalFunctions;
		this.publicFunctions = builder.publicFunctions;
		this.internalFunctions = builder.internalFunctions;
		this.privateFunctions = builder.privateFunctions;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public String getName( )
	{
		return name;
	}

	public Set<String> getInheritedContracts( )
	{
		return inheritedContracts;
	}

	public Set<UsingForElement> getUsingForDeclarations( )
	{
		return usingForDeclarations;
	}

	public Set<EnumElement> getEnumDeclarations( )
	{
		return enumDeclarations;
	}

	public Set<StructElement> getStructDeclarations( )
	{
		return structDeclarations;
	}

	public Set<StateVariableElement> getStateVariables( )
	{
		return stateVariables;
	}

	public Set<EventElement> getEventDeclarations( )
	{
		return eventDeclarations;
	}

	public Set<ModifierElement> getModifierDeclarations( )
	{
		return modifierDeclarations;
	}

	public ConstructorElement getConstructor( )
	{
		return constructor;
	}

	public FunctionElement getFallbackFunction( )
	{
		return fallbackFunction;
	}

	public Set<FunctionElement> getExternalFunctions( )
	{
		return externalFunctions;
	}

	public Set<FunctionElement> getPublicFunctions( )
	{
		return publicFunctions;
	}

	public Set<FunctionElement> getInternalFunctions( )
	{
		return internalFunctions;
	}

	public Set<FunctionElement> getPrivateFunctions( )
	{
		return privateFunctions;
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public static final class Builder
	{
		private final String name;
		private final Set<String> inheritedContracts = new LinkedHashSet<>( );
		private final Set<UsingForElement> usingForDeclarations = new LinkedHashSet<>( );
		private final Set<EnumElement> enumDeclarations = new LinkedHashSet<>( );
		private final Set<StructElement> structDeclarations = new LinkedHashSet<>( );
		private final Set<StateVariableElement> stateVariables = new LinkedHashSet<>( );
		private final Set<EventElement> eventDeclarations = new LinkedHashSet<>( );
		private final Set<ModifierElement> modifierDeclarations = new LinkedHashSet<>( );
		private ConstructorElement constructor;
		private FunctionElement fallbackFunction;
		private final Set<FunctionElement> externalFunctions = new LinkedHashSet<>( );
		private final Set<FunctionElement> publicFunctions = new LinkedHashSet<>( );
		private final Set<FunctionElement> internalFunctions = new LinkedHashSet<>( );
		private final Set<FunctionElement> privateFunctions = new LinkedHashSet<>( );

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addInheritedContract( String name )
		{
			this.inheritedContracts.add( name );
			return this;
		}

		public Builder addUsingForDeclaration( UsingForElement usingForElement )
		{
			this.usingForDeclarations.add( usingForElement );
			return this;
		}

		public Builder addEnumDeclaration( EnumElement enumElement )
		{
			this.enumDeclarations.add( enumElement );
			return this;
		}

		public Builder addStructDeclaration( StructElement struct )
		{
			this.structDeclarations.add( struct );
			return this;
		}

		public Builder addStateVariable( StateVariableElement stateVariable )
		{
			this.stateVariables.add( stateVariable );
			return this;
		}

		public Builder addEventDeclaration( EventElement event )
		{
			this.eventDeclarations.add( event );
			return this;
		}

		public Builder addModifierDeclaration( ModifierElement modifier )
		{
			this.modifierDeclarations.add( modifier );
			return this;
		}

		public Builder addConstructor( ConstructorElement constructor )
		{
			this.constructor = constructor;
			return this;
		}

		public Builder addFallbackFunction( FunctionElement fallbackFunction )
		{
			this.fallbackFunction = fallbackFunction;
			return this;
		}

		public Builder addExternalFunction( FunctionElement function )
		{
			this.externalFunctions.add( function );
			return this;
		}

		public Builder addPublicFunction( FunctionElement function )
		{
			this.publicFunctions.add( function );
			return this;
		}

		public Builder addInternalFunction( FunctionElement function )
		{
			this.internalFunctions.add( function );
			return this;
		}

		public Builder addPrivateFunction( FunctionElement function )
		{
			this.privateFunctions.add( function );
			return this;
		}

		public ContractElement build( )
		{
			return new ContractElement( this );
		}
	}
}
