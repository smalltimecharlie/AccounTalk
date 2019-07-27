import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPerPensionContributions } from 'app/shared/model/per-pension-contributions.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './per-pension-contributions.reducer';

export interface IPerPensionContributionsDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PerPensionContributionsDeleteDialog extends React.Component<IPerPensionContributionsDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.perPensionContributionsEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { perPensionContributionsEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="accounTalkApp.perPensionContributions.delete.question">
          <Translate
            contentKey="accounTalkApp.perPensionContributions.delete.question"
            interpolate={{ id: perPensionContributionsEntity.id }}
          >
            Are you sure you want to delete this PerPensionContributions?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-perPensionContributions" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ perPensionContributions }: IRootState) => ({
  perPensionContributionsEntity: perPensionContributions.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PerPensionContributionsDeleteDialog);
