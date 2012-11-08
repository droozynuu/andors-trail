package com.gpl.rpg.AndorsTrail.model;

import android.util.FloatMath;

import com.gpl.rpg.AndorsTrail.model.actor.ActorTraits;
import com.gpl.rpg.AndorsTrail.util.Range;

public class CombatTraits {
	public static final int STAT_COMBAT_ATTACK_COST = 0;
	public static final int STAT_COMBAT_ATTACK_CHANCE = 1;
	public static final int STAT_COMBAT_CRITICAL_SKILL = 2;
	public static final int STAT_COMBAT_CRITICAL_MULTIPLIER = 3;
	public static final int STAT_COMBAT_DAMAGE_POTENTIAL_MIN = 4;
	public static final int STAT_COMBAT_DAMAGE_POTENTIAL_MAX = 5;
	public static final int STAT_COMBAT_BLOCK_CHANCE = 6;
	public static final int STAT_COMBAT_DAMAGE_RESISTANCE = 7;

	public int attackCost;

	public int attackChance;
	public int criticalSkill;
	public float criticalMultiplier;
	public final Range damagePotential;

	public int blockChance;
	public int damageResistance;
	
	public CombatTraits() {
		this.damagePotential = new Range();
	}
	public CombatTraits(ActorTraits copy) {
		this();
		set(copy);
	}
	public void set(ActorTraits copy) {
		if (copy == null) return;
		this.attackCost = copy.attackCost;
		this.attackChance = copy.attackChance;
		this.criticalSkill = copy.criticalSkill;
		this.criticalMultiplier = copy.criticalMultiplier;
		this.damagePotential.set(copy.damagePotential);
		this.blockChance = copy.blockChance;
		this.damageResistance = copy.damageResistance;
	}
	
	public boolean isSameValuesAs(ActorTraits other) {
		if (other == null) return isZero();
		return 
			this.attackCost == other.attackCost
			&& this.attackChance == other.attackChance
			&& this.criticalSkill == other.criticalSkill
			&& this.criticalMultiplier == other.criticalMultiplier
			&& this.damagePotential.equals(other.damagePotential)
			&& this.blockChance == other.blockChance
			&& this.damageResistance == other.damageResistance;
	}
	
	private boolean isZero() {
		return 
			this.attackCost == 0
			&& this.attackChance == 0
			&& this.criticalSkill == 0
			&& this.criticalMultiplier == 0
			&& this.damagePotential.current == 0
			&& this.damagePotential.max == 0
			&& this.blockChance == 0
			&& this.damageResistance == 0;
	}
	
	public boolean hasAttackChanceEffect() { return attackChance != 0; }
	public boolean hasAttackDamageEffect() { return damagePotential.max != 0; }
	public boolean hasBlockEffect() { return blockChance != 0; }
	public boolean hasCriticalSkillEffect() { return criticalSkill != 0; }
	public boolean hasCriticalMultiplierEffect() { return criticalMultiplier != 0 && criticalMultiplier != 1; }
	public boolean hasCriticalAttacks() { return hasCriticalSkillEffect() && hasCriticalMultiplierEffect(); }

	public int getEffectiveCriticalChance() {
		if (criticalSkill <= 0) return 0;
		int v = (int) (-5 + 2 * FloatMath.sqrt(5*criticalSkill));
		if (v < 0) return 0;
		return v;
	}
	
	public int getAttacksPerTurn(final int maxAP) {
		return (int) Math.floor(maxAP / attackCost);
	}
	
	public int getCombatStats(int statID) {
		switch (statID) {
		case STAT_COMBAT_ATTACK_COST: return attackCost;
		case STAT_COMBAT_ATTACK_CHANCE: return attackChance;
		case STAT_COMBAT_CRITICAL_SKILL: return criticalSkill;
		case STAT_COMBAT_CRITICAL_MULTIPLIER: return (int) FloatMath.floor(criticalMultiplier);
		case STAT_COMBAT_DAMAGE_POTENTIAL_MIN: return damagePotential.current;
		case STAT_COMBAT_DAMAGE_POTENTIAL_MAX: return damagePotential.max;
		case STAT_COMBAT_BLOCK_CHANCE: return blockChance;
		case STAT_COMBAT_DAMAGE_RESISTANCE: return damageResistance;
		}
		return 0;
	}
}
