package com.tobiasfertig.java.solidityartist;

import java.util.LinkedHashSet;
import java.util.Set;

public class LibrarySpec implements Writable
{
	public final static String LIBRARY_KEYWORD = "library";

	private final String name;
	private final Set<UsingForSpec> usingForSpecs;
	private final Set<EnumTypeName> enumDeclarations;
	private final Set<StructTypeName> structDeclarations;
	private final Set<StateVariableSpec> stateVariables;
	private final Set<EventSpec> events;
	private final Set<ModifierSpec> customModifiers;
	private final Set<FunctionSpec> functions;

	private LibrarySpec( Builder builder )
	{
		this.name = builder.name;
		this.usingForSpecs = builder.usingForSpecs;
		this.enumDeclarations = builder.enumDeclarations;
		this.structDeclarations = builder.structDeclarations;
		this.stateVariables = builder.stateVariables;
		this.events = builder.events;
		this.customModifiers = builder.customModifiers;
		this.functions = builder.functions;
	}

	private boolean hasUsingForSpecs( )
	{
		return !usingForSpecs.isEmpty( );
	}

	private boolean hasEnumDeclarations( )
	{
		return !enumDeclarations.isEmpty( );
	}

	private boolean hasStructDeclarations( )
	{
		return !structDeclarations.isEmpty( );
	}

	private boolean hasStateVariables( )
	{
		return !stateVariables.isEmpty( );
	}

	private boolean hasEvents( )
	{
		return !events.isEmpty( );
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
		writer.write( LIBRARY_KEYWORD )
			  .space( )
			  .write( this.name )
			  .space( );

		writer.openCurlyBraces( );

		if ( hasUsingForSpecs( ) )
		{
			for ( UsingForSpec usingForSpec : usingForSpecs )
			{
				writer.write( usingForSpec );
			}

			writer.newline( );
		}

		if ( hasEnumDeclarations( ) )
		{
			for ( EnumTypeName enumDeclaration : enumDeclarations )
			{
				writer.write( enumDeclaration )
					  .newline( )
					  .newline( );
			}
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

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public static final class Builder
	{
		private final String name;
		private final Set<UsingForSpec> usingForSpecs = new LinkedHashSet<>( );
		private final Set<EnumTypeName> enumDeclarations = new LinkedHashSet<>( );
		private final Set<StructTypeName> structDeclarations = new LinkedHashSet<>( );
		private final Set<StateVariableSpec> stateVariables = new LinkedHashSet<>( );
		private final Set<EventSpec> events = new LinkedHashSet<>( );
		private final Set<ModifierSpec> customModifiers = new LinkedHashSet<>( );
		private final Set<FunctionSpec> functions = new LinkedHashSet<>( );

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addUsingForSpec( UsingForSpec usingForSpec )
		{
			this.usingForSpecs.add( usingForSpec );
			return this;
		}

		public Builder addEnumDeclaration( EnumTypeName enumTypeName )
		{
			this.enumDeclarations.add( enumTypeName );
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

		public LibrarySpec build( )
		{
			return new LibrarySpec( this );
		}
	}
}
