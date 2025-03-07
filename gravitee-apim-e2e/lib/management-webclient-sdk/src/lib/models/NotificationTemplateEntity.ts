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
    NotificationTemplateType,
    NotificationTemplateTypeFromJSON,
    NotificationTemplateTypeFromJSONTyped,
    NotificationTemplateTypeToJSON,
} from './';

/**
 * 
 * @export
 * @interface NotificationTemplateEntity
 */
export interface NotificationTemplateEntity {
    /**
     * 
     * @type {string}
     * @memberof NotificationTemplateEntity
     */
    content?: string;
    /**
     * 
     * @type {Date}
     * @memberof NotificationTemplateEntity
     */
    created_at?: Date;
    /**
     * 
     * @type {string}
     * @memberof NotificationTemplateEntity
     */
    description?: string;
    /**
     * 
     * @type {boolean}
     * @memberof NotificationTemplateEntity
     */
    enabled?: boolean;
    /**
     * 
     * @type {string}
     * @memberof NotificationTemplateEntity
     */
    hook?: string;
    /**
     * 
     * @type {string}
     * @memberof NotificationTemplateEntity
     */
    id?: string;
    /**
     * 
     * @type {string}
     * @memberof NotificationTemplateEntity
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof NotificationTemplateEntity
     */
    scope?: string;
    /**
     * 
     * @type {string}
     * @memberof NotificationTemplateEntity
     */
    title?: string;
    /**
     * 
     * @type {NotificationTemplateType}
     * @memberof NotificationTemplateEntity
     */
    type?: NotificationTemplateType;
    /**
     * 
     * @type {Date}
     * @memberof NotificationTemplateEntity
     */
    updated_at?: Date;
}

export function NotificationTemplateEntityFromJSON(json: any): NotificationTemplateEntity {
    return NotificationTemplateEntityFromJSONTyped(json, false);
}

export function NotificationTemplateEntityFromJSONTyped(json: any, ignoreDiscriminator: boolean): NotificationTemplateEntity {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'content': !exists(json, 'content') ? undefined : json['content'],
        'created_at': !exists(json, 'created_at') ? undefined : (new Date(json['created_at'])),
        'description': !exists(json, 'description') ? undefined : json['description'],
        'enabled': !exists(json, 'enabled') ? undefined : json['enabled'],
        'hook': !exists(json, 'hook') ? undefined : json['hook'],
        'id': !exists(json, 'id') ? undefined : json['id'],
        'name': !exists(json, 'name') ? undefined : json['name'],
        'scope': !exists(json, 'scope') ? undefined : json['scope'],
        'title': !exists(json, 'title') ? undefined : json['title'],
        'type': !exists(json, 'type') ? undefined : NotificationTemplateTypeFromJSON(json['type']),
        'updated_at': !exists(json, 'updated_at') ? undefined : (new Date(json['updated_at'])),
    };
}

export function NotificationTemplateEntityToJSON(value?: NotificationTemplateEntity | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'content': value.content,
        'created_at': value.created_at === undefined ? undefined : (value.created_at.toISOString()),
        'description': value.description,
        'enabled': value.enabled,
        'hook': value.hook,
        'id': value.id,
        'name': value.name,
        'scope': value.scope,
        'title': value.title,
        'type': NotificationTemplateTypeToJSON(value.type),
        'updated_at': value.updated_at === undefined ? undefined : (value.updated_at.toISOString()),
    };
}


