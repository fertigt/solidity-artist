package com.tobiasfertig.java.solidityartist.elements.files;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.functions.FunctionElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ModifierElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import com.tobiasfertig.java.solidityartist.utils.FunctionElementComparator;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class InterfaceElement implements SolidityElement
{
	private final NatSpecElement comment;
	private final String name;
	private final Set<UsingForElement> usingForDeclarations;
	private final Set<EnumElement> enumDeclarations;
	private final Set<StructElement> structDeclarations;
	private final Set<EventElement> eventDeclarations;
	private final Set<ModifierElement> modifierDeclarations;
	private final FunctionElement fallbackFunction;
	private final Set<FunctionElement> externalFunctions;

	private InterfaceElement( Builder builder )
	{
		this.comment = builder.comment;
		this.name = builder.name;
		this.usingForDeclarations = builder.usingForDeclarations;
		this.enumDeclarations = builder.enumDeclarations;
		this.structDeclarations = builder.structDeclarations;
		this.eventDeclarations = builder.eventDeclarations;
		this.modifierDeclarations = builder.modifierDeclarations;
		this.fallbackFunction = builder.fallbackFunction;
		this.externalFunctions = builder.externalFunctions;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public NatSpecElement getComment( )
	{
		return comment;
	}

	public String getName( )
	{
		return name;
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

	public Set<EventElement> getEventDeclarations( )
	{
		return eventDeclarations;
	}

	public Set<ModifierElement> getModifierDeclarations( )
	{
		return modifierDeclarations;
	}

	public FunctionElement getFallbackFunction( )
	{
		return fallbackFunction;
	}

	public Set<FunctionElement> getExternalFunctions( )
	{
		return externalFunctions;
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public static final class Builder
	{
		private NatSpecElement comment;
		private final String name;
		private final Set<UsingForElement> usingForDeclarations = new LinkedHashSet<>( );
		private final Set<EnumElement> enumDeclarations = new LinkedHashSet<>( );
		private final Set<StructElement> structDeclarations = new LinkedHashSet<>( );
		private final Set<EventElement> eventDeclarations = new LinkedHashSet<>( );
		private final Set<ModifierElement> modifierDeclarations = new LinkedHashSet<>( );
		private FunctionElement fallbackFunction;
		private final Set<FunctionElement> externalFunctions = new TreeSet<>( new FunctionElementComparator( ) );

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addNatSpec( NatSpecElement comment )
		{
			this.comment = comment;
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

		public InterfaceElement build( )
		{
			return new InterfaceElement( this );
		}
	}

}
