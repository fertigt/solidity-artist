package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

import java.util.LinkedHashSet;
import java.util.Set;

public class InterfaceSpec implements Writable
{
	private final String name;
	private final Set<UsingForSpec> usingForSpecs;
	private final Set<EnumTypeName> enumDeclarations;
	private final Set<StructTypeName> structDeclarations;
	private final Set<EventSpec> events;
	private final FunctionSpec fallbackFunction;
	private final Set<ModifierSpec> customModifiers;
	private final Set<FunctionSpec> functions;

	private InterfaceSpec( Builder builder )
	{
		this.name = builder.name;
		this.usingForSpecs = builder.usingForSpecs;
		this.enumDeclarations = builder.enumDeclarations;
		this.structDeclarations = builder.structDeclarations;
		this.events = builder.events;
		this.fallbackFunction = builder.fallbackFunctionSpec;
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

	@Override public void write( CodeWriter writer )
	{
		writer.write( Keywords.INTERFACE )
			  .space( )
			  .write( this.name )
			  .space( )
			  .openCurlyBraces( );

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

		if ( hasEvents( ) )
		{
			for ( EventSpec event : events )
			{
				writer.write( event );
			}

			writer.newline( );
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
		private final Set<EventSpec> events = new LinkedHashSet<>( );
		private FunctionSpec fallbackFunctionSpec;
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

		public Builder addEvent( EventSpec eventSpec )
		{
			this.events.add( eventSpec );
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

		public InterfaceSpec build( )
		{
			return new InterfaceSpec( this );
		}
	}
}
