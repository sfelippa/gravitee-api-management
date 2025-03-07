/* tslint:disable */
/* eslint-disable */
/**
 * Gravitee.io - Management API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
import {
    GroupMappingEntity,
    GroupMappingEntityFromJSON,
    GroupMappingEntityFromJSONTyped,
    GroupMappingEntityToJSON,
    IdentityProviderType,
    IdentityProviderTypeFromJSON,
    IdentityProviderTypeFromJSONTyped,
    IdentityProviderTypeToJSON,
    RoleMappingEntity,
    RoleMappingEntityFromJSON,
    RoleMappingEntityFromJSONTyped,
    RoleMappingEntityToJSON,
} from './';

/**
 * 
 * @export
 * @interface IdentityProviderEntity
 */
export interface IdentityProviderEntity {
    /**
     * 
     * @type {{ [key: string]: any; }}
     * @memberof IdentityProviderEntity
     */
    configuration?: { [key: string]: any; };
    /**
     * 
     * @type {Date}
     * @memberof IdentityProviderEntity
     */
    created_at?: Date;
    /**
     * 
     * @type {string}
     * @memberof IdentityProviderEntity
     */
    description?: string;
    /**
     * 
     * @type {boolean}
     * @memberof IdentityProviderEntity
     */
    emailRequired?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof IdentityProviderEntity
     */
    enabled?: boolean;
    /**
     * 
     * @type {Array<GroupMappingEntity>}
     * @memberof IdentityProviderEntity
     */
    groupMappings?: Array<GroupMappingEntity>;
    /**
     * 
     * @type {string}
     * @memberof IdentityProviderEntity
     */
    id?: string;
    /**
     * 
     * @type {string}
     * @memberof IdentityProviderEntity
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof IdentityProviderEntity
     */
    organization?: string;
    /**
     * 
     * @type {Array<RoleMappingEntity>}
     * @memberof IdentityProviderEntity
     */
    roleMappings?: Array<RoleMappingEntity>;
    /**
     * 
     * @type {boolean}
     * @memberof IdentityProviderEntity
     */
    syncMappings?: boolean;
    /**
     * 
     * @type {IdentityProviderType}
     * @memberof IdentityProviderEntity
     */
    type?: IdentityProviderType;
    /**
     * 
     * @type {Date}
     * @memberof IdentityProviderEntity
     */
    updated_at?: Date;
    /**
     * 
     * @type {{ [key: string]: string; }}
     * @memberof IdentityProviderEntity
     */
    userProfileMapping?: { [key: string]: string; };
}

export function IdentityProviderEntityFromJSON(json: any): IdentityProviderEntity {
    return IdentityProviderEntityFromJSONTyped(json, false);
}

export function IdentityProviderEntityFromJSONTyped(json: any, ignoreDiscriminator: boolean): IdentityProviderEntity {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'configuration': !exists(json, 'configuration') ? undefined : json['configuration'],
        'created_at': !exists(json, 'created_at') ? undefined : (new Date(json['created_at'])),
        'description': !exists(json, 'description') ? undefined : json['description'],
        'emailRequired': !exists(json, 'emailRequired') ? undefined : json['emailRequired'],
        'enabled': !exists(json, 'enabled') ? undefined : json['enabled'],
        'groupMappings': !exists(json, 'groupMappings') ? undefined : ((json['groupMappings'] as Array<any>).map(GroupMappingEntityFromJSON)),
        'id': !exists(json, 'id') ? undefined : json['id'],
        'name': !exists(json, 'name') ? undefined : json['name'],
        'organization': !exists(json, 'organization') ? undefined : json['organization'],
        'roleMappings': !exists(json, 'roleMappings') ? undefined : ((json['roleMappings'] as Array<any>).map(RoleMappingEntityFromJSON)),
        'syncMappings': !exists(json, 'syncMappings') ? undefined : json['syncMappings'],
        'type': !exists(json, 'type') ? undefined : IdentityProviderTypeFromJSON(json['type']),
        'updated_at': !exists(json, 'updated_at') ? undefined : (new Date(json['updated_at'])),
        'userProfileMapping': !exists(json, 'userProfileMapping') ? undefined : json['userProfileMapping'],
    };
}

export function IdentityProviderEntityToJSON(value?: IdentityProviderEntity | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'configuration': value.configuration,
        'created_at': value.created_at === undefined ? undefined : (value.created_at.toISOString()),
        'description': value.description,
        'emailRequired': value.emailRequired,
        'enabled': value.enabled,
        'groupMappings': value.groupMappings === undefined ? undefined : ((value.groupMappings as Array<any>).map(GroupMappingEntityToJSON)),
        'id': value.id,
        'name': value.name,
        'organization': value.organization,
        'roleMappings': value.roleMappings === undefined ? undefined : ((value.roleMappings as Array<any>).map(RoleMappingEntityToJSON)),
        'syncMappings': value.syncMappings,
        'type': IdentityProviderTypeToJSON(value.type),
        'updated_at': value.updated_at === undefined ? undefined : (value.updated_at.toISOString()),
        'userProfileMapping': value.userProfileMapping,
    };
}


