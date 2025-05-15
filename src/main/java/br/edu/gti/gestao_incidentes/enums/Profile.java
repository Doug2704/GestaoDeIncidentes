package br.edu.gti.gestao_incidentes.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Profile implements GrantedAuthority {

    DEFAULT("Usuário sem perfil atribuido. Possui permissão apenas para VISUALIZAR planos de ação"),
    OPERATOR("Operador do sistema. Possui permissões para VISUALIZAR e EXECUTAR planos de ação."),
    MANAGER("Gerenciador do sistema. Possui permissões para CRIAR, VISUALIZAR e ATUALIZAR planos de ação."),
    ADMIN("Administrador do sistema. Possui permissões para VISUALIZAR e EXCLUIR planos de ação," +
            " além de gerenciar usuários, áreas e ativos");

    private final String description;

    Profile(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getAuthority() {
        return name();
    }
    }
