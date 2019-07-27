import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDividendsReceived } from 'app/shared/model/dividends-received.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './dividends-received.reducer';

export interface IDividendsReceivedDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DividendsReceivedDeleteDialog extends React.Component<IDividendsReceivedDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.dividendsReceivedEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { dividendsReceivedEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="accounTalkApp.dividendsReceived.delete.question">
          <Translate contentKey="accounTalkApp.dividendsReceived.delete.question" interpolate={{ id: dividendsReceivedEntity.id }}>
            Are you sure you want to delete this DividendsReceived?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-dividendsReceived" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ dividendsReceived }: IRootState) => ({
  dividendsReceivedEntity: dividendsReceived.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DividendsReceivedDeleteDialog);
