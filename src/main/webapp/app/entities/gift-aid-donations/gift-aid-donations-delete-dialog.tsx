import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IGiftAidDonations } from 'app/shared/model/gift-aid-donations.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './gift-aid-donations.reducer';

export interface IGiftAidDonationsDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class GiftAidDonationsDeleteDialog extends React.Component<IGiftAidDonationsDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.giftAidDonationsEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { giftAidDonationsEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="accounTalkApp.giftAidDonations.delete.question">
          <Translate contentKey="accounTalkApp.giftAidDonations.delete.question" interpolate={{ id: giftAidDonationsEntity.id }}>
            Are you sure you want to delete this GiftAidDonations?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-giftAidDonations" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ giftAidDonations }: IRootState) => ({
  giftAidDonationsEntity: giftAidDonations.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GiftAidDonationsDeleteDialog);
